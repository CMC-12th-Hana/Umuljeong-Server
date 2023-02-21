package cmc.hana.umuljeong.web.dto;

import lombok.*;

public class ClientCompanyRequestDto {

    @Getter
    public static class SalesRepresentativeDto {
        private String name;

        private String phoneNumber;

        private String department;
    }

    @Getter
    public static class CreateClientCompanyDto {
        private String name;
        private String tel;
        private SalesRepresentativeDto salesRepresentativeDto;
    }

    @Getter
    public static class UpdateClientCompanyDto {
        private String name;
        private String tel;
        private SalesRepresentativeDto salesRepresentativeDto;
    }
}
