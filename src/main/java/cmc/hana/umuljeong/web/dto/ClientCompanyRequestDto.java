package cmc.hana.umuljeong.web.dto;

import lombok.*;

public class ClientCompanyRequestDto {

    private static class SalesRepresentative {
        private String name;

        private String phoneNumber;

        private String department;
    }

    @Builder
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class CreateClientCompanyDto {
        private String name;
        private String tel;
        private SalesRepresentative salesRepresentative;
    }
}
