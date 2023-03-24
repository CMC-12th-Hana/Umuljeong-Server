package cmc.hana.umuljeong.web.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        private Long revenue;
        @Size(min = 0, max = 300, message = "300자 이하로 작성해주세요.")
        private String description;
    }

    @Getter
    public static class UpdateBusinessDto {
        @NotBlank
        private String name;
        private BusinessPeriodDto businessPeriodDto;
        private List<Long> memberIdList;
        @NotNull
        private Long revenue;
        @Size(min = 0, max = 300, message = "300자 이하로 작성해주세요.")
        private String description;
    }
}
