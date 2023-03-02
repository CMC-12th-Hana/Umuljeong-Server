package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class MemberRequestDto {

    @Getter
    public static class CreateDto {
        @NotBlank
        private String name;
        @NotBlank
        private String phoneNumber;
        @NotBlank
        private String staffRank;
        @NotBlank
        private String staffNumber;
    }

    @Getter
    public static class UpdateProfileDto {
        @NotBlank
        private String name;
        @NotBlank
        private String staffNumber;
    }
}
