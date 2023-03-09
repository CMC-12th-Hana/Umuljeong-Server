package cmc.hana.umuljeong.web.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ClientCompanyRequestDto {

    @Getter
    public static class SalesRepresentativeDto {
        private String name;
        private String phoneNumber;
        private String department;
    }

    @Getter
    public static class CreateClientCompanyDto {
        @NotBlank
        private String name;
        @NotBlank
        private String tel;
        private SalesRepresentativeDto salesRepresentativeDto;
    }

    @Getter
    public static class UpdateClientCompanyDto {
        @NotBlank
        private String name;
        @NotBlank
        private String tel;

        private SalesRepresentativeDto salesRepresentativeDto;
    }
}
