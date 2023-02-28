package cmc.hana.umuljeong.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthRequestDto {

    @Getter
    public static class LoginDto {
        @NotBlank
        @Size(min = 11, max = 11)
        private String phoneNumber;

        @NotBlank
        @Size(min = 8, max = 20)
        // todo : 8-20자 영문 대소문자, 숫자, 특수문자 조합
        private String password;
    }

    @Getter
    public static class JoinDto {
        @NotBlank
        private String name;
        @NotBlank
        @Size(min = 11, max = 11)
        private String phoneNumber;
        @NotBlank
        @Size(min = 8, max = 20)
        // todo : 8-20자 영문 대소문자, 숫자, 특수문자 조합
        private String password;
        @NotBlank
        @Size(min = 8, max = 20)
        // todo : 8-20자 영문 대소문자, 숫자, 특수문자 조합
        private String passwordCheck;
    }
}
