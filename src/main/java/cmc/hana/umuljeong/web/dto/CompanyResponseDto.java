package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class CompanyResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CompanyCreateDto {
        private Long companyId;
        private LocalDateTime createdAt;
    }
}
