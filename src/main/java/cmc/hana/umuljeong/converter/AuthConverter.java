package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.web.dto.AuthResponseDto;

public class AuthConverter {

    public static AuthResponseDto.LoginDto toLoginDto(String token) {
        return AuthResponseDto.LoginDto.builder()
                .accessToken(token)
                .build();
    }
}
