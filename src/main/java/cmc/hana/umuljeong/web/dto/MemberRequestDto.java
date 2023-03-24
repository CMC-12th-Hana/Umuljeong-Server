package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MemberRequestDto {

    @Getter
    public static class CreateDto {
        @NotBlank
        private String name;
        @NotBlank
        @Pattern(regexp = "^01([016789])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
        private String phoneNumber;
        private String staffRank;
        private String staffNumber;
    }

    @Getter
    public static class UpdateProfileDto {
        @NotBlank
        private String name;
        private String staffNumber;
        private String staffRank;
    }

    @Getter
    public static class UpdateMemberProfileDto {
        @NotBlank
        private String name;
        @NotBlank
        @Pattern(regexp = "^01([016789])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
        private String phoneNumber;
        private String staffRank;
        private String staffNumber;
    }

    @Getter
    public static class UpdatePasswordDto {
        @NotBlank
        @Size(min = 8, max = 20)
        @Pattern.List({
                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+", message = "영문 대소문자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[0-9]).+", message = "숫자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[-+_!@#\\$%^&*., ?]).+", message = "특수문자를 포함해주세요")
        })
        private String password;
        @NotBlank
        @Size(min = 8, max = 20)
        @Pattern.List({
                @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+", message = "영문 대소문자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[0-9]).+", message = "숫자를 포함해주세요."),
                @Pattern(regexp = "^(?=.*[-+_!@#\\$%^&*., ?]).+", message = "특수문자를 포함해주세요")
        })
        private String passwordCheck;
    }
}
