package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.VerificationMessage;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.repository.VerificationMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthValidator {

    private final VerificationMessageRepository verificationMessageRepository;
    private static VerificationMessageRepository staticVerificationMessageRepository;

    @PostConstruct
    public void init() {
       this.staticVerificationMessageRepository = this.verificationMessageRepository;
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
