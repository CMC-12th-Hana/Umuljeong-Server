package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class BusinessResponseDto {
    public static class BusinessListDto {
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateBusinessDto {
        private Long businessId;
        private LocalDateTime createdAt;
    }

    public static class BusinessDto {
    }
}
