package cmc.hana.umuljeong.web.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClientCompanyRequestDto {

    @Getter
    public static class SalesRepresentativeDto {
        private String name;
        @Pattern(regexp = "^01([016789])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 휴대폰 번호 형식이 아닙니다.")
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
