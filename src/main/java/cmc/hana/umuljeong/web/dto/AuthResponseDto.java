package cmc.hana.umuljeong.web.dto;

import lombok.*;

public class AuthResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDto {
        private String accessToken;
        // todo : refreshToken
    }
}
