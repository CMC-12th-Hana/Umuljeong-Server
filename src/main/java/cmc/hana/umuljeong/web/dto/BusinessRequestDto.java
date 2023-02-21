package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class BusinessRequestDto {

    @Getter
    public static class BusinessPeriodDto {
        private LocalDate start;
        private LocalDate finish;
    }

    @Getter
    public static class CreateBusinessDto {
        private String name;
        private BusinessPeriodDto businessPeriodDto;
        private List<Long> memberIdList;
        private Integer revenue;
        private String description;
    }
}
