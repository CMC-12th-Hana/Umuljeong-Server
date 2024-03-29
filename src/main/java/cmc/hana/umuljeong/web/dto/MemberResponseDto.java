package cmc.hana.umuljeong.web.dto;

import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProfileDto {
        private Long companyId;
        private Long memberId;
        private String name;
        private String role;
        private String companyName;
        private String staffRank;
        private JoinCompanyStatus joinCompanyStatus;
        // todo : 사내 전화번호?....
        private String phoneNumber;
        private String staffNumber;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ProfileListDto {
        private List<ProfileDto> profileDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateDto {
        private Long memberId;
        private String memberName;
        private String companyName;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateProfileDto {
        private Long memberId;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DeleteDto {
        private LocalDateTime deletedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdatePasswordDto {
        private Long memberId;
        private LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRoleDto {
        private Long memberId;
        private String role;
        private LocalDateTime updatedAt;
    }
}
