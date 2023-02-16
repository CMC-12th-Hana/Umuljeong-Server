package cmc.hana.umuljeong.web.dto;

import lombok.*;

public class AuthRequestDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDto {
        private String phoneNumber;
        private String password;
    }
}
