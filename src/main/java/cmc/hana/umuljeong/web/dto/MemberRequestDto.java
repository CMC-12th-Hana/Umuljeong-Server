package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class MemberRequestDto {

    @Getter
    public static class CreateDto {
        @NotBlank
        private String name;
        @NotBlank
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
        private String phoneNumber;
        private String staffRank;
        private String staffNumber;
    }

    @Getter
    public static class UpdatePasswordDto {
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
