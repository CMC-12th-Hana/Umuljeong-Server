package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.exception.MessageException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.service.MessageService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MessageServiceImpl implements MessageService {

    private final DefaultMessageService coolsmsService;
    private final String fromNumber;

    public MessageServiceImpl(@Value("${cool-sms.api-key}") String apiKey,
                              @Value("${cool-sms.api-secret}") String apiSecret,
                              @Value("${cool-sms.from-number}") String fromNumber,
                              @Value("${cool-sms.domain}") String domain) {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        this.coolsmsService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, domain);
        this.fromNumber = fromNumber;
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

    public void sendMessage(String toNumber) {
        try {
            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom(fromNumber);
            message.setTo(toNumber);
            message.setText("[FIELD MATE]\n인증번호 : " + numberGen(4));

            SingleMessageSentResponse response = this.coolsmsService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            System.out.println(e);
            throw new MessageException(ErrorCode.MESSAGE_SEND_FAILED);
        }
    }


    @Override
    public VerifyMessageStatus verifyMessage(AuthRequestDto.VerifyMessageDto request) {
        return null;
    }
}
