package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class BusinessRequestDto {

    @Getter
    public static class BusinessPeriodDto {
        // todo : start가 finish보다 앞서는 날짜인지 검증
        private LocalDate start;
        private LocalDate finish;
    }

    @Getter
    public static class CreateBusinessDto {
        @NotBlank
        private String name;
        private BusinessPeriodDto businessPeriodDto;
        private List<Long> memberIdList;
        @NotNull
        private Integer revenue;
        private String description;
    }

    @Getter
    public static class UpdateBusinessDto {
        @NotBlank
        private String name;
        private BusinessPeriodDto businessPeriodDto;
        private List<Long> memberIdList;
        @NotNull
        private Integer revenue;
        private String description;
    }
}
