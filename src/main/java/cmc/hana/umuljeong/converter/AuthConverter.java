package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
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

    public static AuthResponseDto.VerifyMessageDto toVerifyMessageDto(VerifyMessageStatus verifyMessageStatus) {
        return AuthResponseDto.VerifyMessageDto.builder()
                .verifyMessageStatus(verifyMessageStatus)
                .build();
    }
}
