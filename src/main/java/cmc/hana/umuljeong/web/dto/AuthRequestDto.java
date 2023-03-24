package cmc.hana.umuljeong.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
        @Pattern(regexp = "^01([016789])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
        private String phoneNumber;
        @NotBlank
        @Size(min = 8, max = 20, message = "8자리 이상 20자리 이하로 입력해주세요.")
        @Pattern.List({
                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+", message = "영문 대소문자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[0-9]).+", message = "숫자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[-+_!@#\\$%^&*., ?]).+", message = "특수문자를 포함해주세요")
        })
        private String password;
        @NotBlank
        @Size(min = 8, max = 20, message = "8자리 이상 20자리 이하로 입력해주세요.")
        @Pattern.List({
                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+", message = "영문 대소문자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[0-9]).+", message = "숫자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[-+_!@#\\$%^&*., ?]).+", message = "특수문자를 포함해주세요")
        })
        private String passwordCheck;
    }

    public enum MessageType {
        JOIN, PASSWORD;
    }

    @Getter
    public static class SendMessageDto {
        @NotBlank
        @Pattern(regexp = "^01([016789])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
        private String phoneNumber;
        private MessageType messageType;
    }

    @Getter
    public static class VerifyMessageDto {
        @NotBlank
        @Pattern(regexp = "^01([016789])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
        private String phoneNumber;

        @NotBlank
        private String authenticationNumber;

        private MessageType messageType;
    }

    @Getter
    public static class RefreshDto {
        @NotBlank
        private String refreshToken;
    }
}
