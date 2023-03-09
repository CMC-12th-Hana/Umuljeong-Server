package cmc.hana.umuljeong.web.dto;

import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.domain.enums.VerifyMessageStatus;
import lombok.*;

import java.time.LocalDateTime;

public class AuthResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDto {
        private String accessToken;
        // todo : refreshToken
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class JoinDto {
        private Long memberId;
        private String accessToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class JoinCompanyDto {
        private Long memberId;
        private JoinCompanyStatus joinCompanyStatus;
        private LocalDateTime joinedAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SendMessageDto {
        private LocalDateTime sentAt;
    }


    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class VerifyMessageDto {
        private VerifyMessageStatus verifyMessageStatus;
    }

}
