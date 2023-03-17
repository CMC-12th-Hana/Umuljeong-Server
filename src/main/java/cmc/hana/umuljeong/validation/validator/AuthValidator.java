package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.VerificationMessage;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.repository.VerificationMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private final VerificationMessageRepository verificationMessageRepository;
    private final MemberRepository memberRepository;
    private static VerificationMessageRepository staticVerificationMessageRepository;
    private static MemberRepository staticMemberRepository;

    public static boolean existsByJoinCompanyStatusAndPhoneNumber(String phoneNumber) {
        return staticMemberRepository.existsByJoinCompanyStatusAndPhoneNumber(JoinCompanyStatus.JOINED, phoneNumber);
    }

    @PostConstruct
    public void init() {
       this.staticVerificationMessageRepository = this.verificationMessageRepository;
       this.staticMemberRepository = this.memberRepository;
    }

    public static boolean isVerified(String phoneNumber) {
        Optional<VerificationMessage> optionalVerificationMessage =
                staticVerificationMessageRepository.findByPhoneNumber(phoneNumber);

        if(optionalVerificationMessage.isEmpty()) return false;
        if(optionalVerificationMessage.isPresent()) {
            VerificationMessage verificationMessage = optionalVerificationMessage.get();
            if(verificationMessage.getVerificationJoin() == VerifyMessageStatus.VERIFIED) return true;
            else return false;
        }
        return false;
    }
}
