package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ClientCompanyResponseDto {

    public static class ClientCompanyListDto {
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateClientCompany {
        private Long clientCompanyId;
        private LocalDateTime createdAt;
    }

    public static class UpdateClientCompany {
    }
}
