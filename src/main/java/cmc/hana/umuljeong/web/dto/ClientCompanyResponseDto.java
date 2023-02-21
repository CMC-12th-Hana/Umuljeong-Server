package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ClientCompanyResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SalesRepresentativeDto {
        private String name;
        private String phoneNumber;
        private String department;

    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ClientCompanyDto {
        private String name;
        private String tel;
        private SalesRepresentativeDto salesRepresentativeDto;
        // todo : 기술자 담당 전화?
        private Integer taskCount;
        private Integer businessCount;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ClientCompanyListDto {
        List<ClientCompanyDto> clientCompanyDtoList;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateClientCompany {
        private Long clientCompanyId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateClientCompany {
        private Long clientCompanyId;
        private LocalDateTime updatedAt;
    }
}
