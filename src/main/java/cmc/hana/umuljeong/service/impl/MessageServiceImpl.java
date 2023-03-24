package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.AuthConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.VerificationMessage;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.exception.MemberException;
import cmc.hana.umuljeong.exception.MessageException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.repository.VerificationMessageRepository;
import cmc.hana.umuljeong.service.MessageService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static cmc.hana.umuljeong.web.dto.AuthRequestDto.MessageType.JOIN;
import static cmc.hana.umuljeong.web.dto.AuthRequestDto.MessageType.PASSWORD;

@Service
public class MessageServiceImpl implements MessageService {

    private final DefaultMessageService coolsmsService;
    private final String fromNumber;

    private final MemberRepository memberRepository;

    private final VerificationMessageRepository verificationMessageRepository;
    private final PasswordEncoder passwordEncoder;

    public MessageServiceImpl(@Value("${cool-sms.api-key}") String apiKey,
                              @Value("${cool-sms.api-secret}") String apiSecret,
                              @Value("${cool-sms.from-number}") String fromNumber,
                              @Value("${cool-sms.domain}") String domain,
                              MemberRepository memberRepository,
                              VerificationMessageRepository verificationMessageRepository,
                              PasswordEncoder passwordEncoder) {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.coolsmsService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, domain);
        this.fromNumber = fromNumber;
        this.memberRepository = memberRepository;
        this.verificationMessageRepository = verificationMessageRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * 전달된 파라미터에 맞게 난수를 생성한다
     * @param len : 생성할 난수의 길이
     *
     * Created by 닢향
     * http://niphyang.tistory.com
     */
    private String numberGen(int len) {
        Random rand = new Random();
        String numStr = ""; //난수가 저장될 변수

        for(int i=0;i<len;i++) {
            //0~9 까지 난수 생성
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        return numStr;
    }

    @Transactional
    public void sendMessage(AuthRequestDto.SendMessageDto request) {
        try {
            String verificationNumber = numberGen(4);
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(fromNumber);
            message.setTo(request.getPhoneNumber());
            message.setText("[FIELD MATE]\n인증번호 : " + verificationNumber);
            SingleMessageSentResponse response = this.coolsmsService.sendOne(new SingleMessageSendingRequest(message));

            Optional<VerificationMessage> optionalVerificationMessage = verificationMessageRepository.findByPhoneNumber(request.getPhoneNumber());
            if(optionalVerificationMessage.isPresent()) {
                VerificationMessage verificationMessage = optionalVerificationMessage.get();
                verificationMessage.setVerificationNumber(verificationNumber);
                verificationMessage.setExpirationTime(LocalDateTime.now().plusMinutes(5));
                if(request.getMessageType() == JOIN) verificationMessage.setVerificationJoin(VerifyMessageStatus.PENDING);
                else verificationMessage.setVerificationPassword(VerifyMessageStatus.PENDING);
            } else {
                VerificationMessage verificationMessage = AuthConverter.toVerificationMessage(request, verificationNumber);
                verificationMessageRepository.save(verificationMessage);
            }
        } catch (Exception e) {
            throw new MessageException(ErrorCode.MESSAGE_SEND_FAILED);
        }
    }


    @Transactional
    @Override
    public VerifyMessageStatus verifyMessage(AuthRequestDto.VerifyMessageDto request) {
        Optional<VerificationMessage> optionalVerificationMessage = verificationMessageRepository.findByPhoneNumber(request.getPhoneNumber());
        if(optionalVerificationMessage.isPresent()) {
            VerificationMessage verificationMessage = optionalVerificationMessage.get();
            if(LocalDateTime.now().isAfter(verificationMessage.getExpirationTime())) throw new MessageException(ErrorCode.MESSAGE_VERIFICATION_TIMEOUT);

            if(verificationMessage.getVerificationNumber().equals(request.getAuthenticationNumber())) {
                switch (request.getMessageType()) {
                    case JOIN:
                        verificationMessage.setVerificationJoin(VerifyMessageStatus.VERIFIED);
                        return VerifyMessageStatus.VERIFIED;
                    case PASSWORD:
                        verificationMessage.setVerificationPassword(VerifyMessageStatus.VERIFIED);
                        return VerifyMessageStatus.VERIFIED;
                    default:
                        if(request.getMessageType() == JOIN) verificationMessage.setVerificationJoin(VerifyMessageStatus.FAILED);
                        if(request.getMessageType() == PASSWORD) verificationMessage.setVerificationJoin(VerifyMessageStatus.FAILED);
                }
            }
            else throw new MessageException(ErrorCode.VERIFICATION_DID_NOT_MATCH);
        }
        throw new MessageException(ErrorCode.MESSAGE_NOT_FOUND);
    }

    @Transactional
    @Override
    public void sendTempPassword(String phoneNumber) {
        VerificationMessage verificationMessage = verificationMessageRepository.findByPhoneNumber(phoneNumber).get();
        Member member = memberRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));
        String newPassword = numberGen(8);
        try {
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(fromNumber);
            message.setTo(phoneNumber);
            message.setText("[FIELD MATE]\n임시비밀번호 : " + newPassword);
            SingleMessageSentResponse response = this.coolsmsService.sendOne(new SingleMessageSendingRequest(message));

            verificationMessage.setVerificationPassword(VerifyMessageStatus.PENDING);
            member.setPassword(passwordEncoder.encode(newPassword));
        } catch (Exception e) {
            throw new MessageException(ErrorCode.MESSAGE_SEND_FAILED);
        }

    }
}
