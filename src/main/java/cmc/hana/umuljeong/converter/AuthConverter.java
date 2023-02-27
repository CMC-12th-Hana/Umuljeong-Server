package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.AuthResponseDto;

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
}
