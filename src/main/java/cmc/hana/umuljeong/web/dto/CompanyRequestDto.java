package cmc.hana.umuljeong.web.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

public class CompanyRequestDto {

    @Getter
    public static class CompanyCreateDto {
        @NotBlank
        private String name;
    }
}
