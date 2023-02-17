package cmc.hana.umuljeong.web.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BusinessResponseDto {

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateBusinessDto {
        private Long businessId;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BusinessPeriodDto {
        private LocalDate start;
        private LocalDate finish;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberDto {
        private Long id;
        private String name;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BusinessDto {
        private String name;
        private BusinessPeriodDto businessPeriodDto;
        private Integer revenue;
        private List<MemberDto> memberDtoList;
        private String description;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BusinessListDto {
        private List<BusinessDto> businessDtoList;
    }

}
