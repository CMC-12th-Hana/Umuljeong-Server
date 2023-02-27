package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

public class MemberRequestDto {

    @Getter
    public static class CreateDto {
        private String name;
        private String phoneNumber;
        private String staffRank;
        private String staffNumber;
    }

    @Getter
    public class UpdateProfileDto {
        private String name;
        private String staffNumber;
    }
}
