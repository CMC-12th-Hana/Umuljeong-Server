package cmc.hana.umuljeong.web.dto;

import lombok.*;

public class AuthRequestDto {

    @Getter
    public static class LoginDto {
        private String phoneNumber;
        private String password;
    }

    @Getter
    public static class JoinDto {
        private String name;
        private String phoneNumber;
        private String password;
        private String passwordCheck;
    }
}
