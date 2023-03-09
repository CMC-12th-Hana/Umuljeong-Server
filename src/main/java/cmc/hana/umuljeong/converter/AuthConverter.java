package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.VerificationMessage;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.AuthResponseDto;

import java.time.LocalDateTime;

public class AuthConverter {

    public static AuthResponseDto.LoginDto toLoginDto(String token) {
        return AuthResponseDto.LoginDto.builder()
                .accessToken(token)
                .build();
    }

    public static AuthResponseDto.JoinDto toJoinDto(Member member, String accessToken) {
        return AuthResponseDto.JoinDto.builder()
                .memberId(member.getId())
                .accessToken(accessToken)
                .build();
    }

    public static AuthResponseDto.JoinCompanyDto toJoinCompanyDto(Member joinedMember) {
        return AuthResponseDto.JoinCompanyDto.builder()
                .memberId(joinedMember.getId())
                .joinCompanyStatus(joinedMember.getJoinCompanyStatus())
                .joinedAt(joinedMember.getUpdatedAt())
                .build();
    }

    public static AuthResponseDto.SendMessageDto toSendMessageDto() {
        return AuthResponseDto.SendMessageDto.builder()
                .sentAt(LocalDateTime.now())
                .build();
    }

    public static AuthResponseDto.VerifyMessageDto toJoinVerifyMessageDto(VerifyMessageStatus verifyMessageStatus) {
        AuthResponseDto.VerifyMessageDto joinVerifyMessageDto =
                AuthResponseDto.VerifyMessageDto.builder()
                    .verifyMessageStatus(verifyMessageStatus)
                    .build();

        return joinVerifyMessageDto;
    }

    public static VerificationMessage toVerificationMessage(AuthRequestDto.SendMessageDto request, String verificationNumber) {
        return VerificationMessage.builder()
                .verificationJoin(VerifyMessageStatus.PENDING)
                .verificationPassword(VerifyMessageStatus.PENDING)
                .verificationNumber(verificationNumber)
                .ExpirationTime(LocalDateTime.now().plusMinutes(5))
                .phoneNumber(request.getPhoneNumber())
                .build();
    }
}
