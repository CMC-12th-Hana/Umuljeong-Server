package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.embedded.SalesRepresentative;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;

public class ClientCompanyConverter {

    public static ClientCompanyResponseDto.CreateClientCompany toCreateClientCompany(ClientCompany clientCompany) {
        return ClientCompanyResponseDto.CreateClientCompany.builder()
                .clientCompanyId(clientCompany.getId())
                .createdAt(clientCompany.getCreatedAt())
                .build();
    }

    private static ClientCompanyResponseDto.SalesRepresentativeDto toSalesRepresentativeDto(SalesRepresentative salesRepresentative) {
        return ClientCompanyResponseDto.SalesRepresentativeDto.builder()
                .name(salesRepresentative.getName())
                .phoneNumber(salesRepresentative.getPhoneNumber())
                .department(salesRepresentative.getDepartment())
                .build();
    }

    public static ClientCompanyResponseDto.ClientCompanyDto toClientCompanyDto(ClientCompany clientCompany) {
        return ClientCompanyResponseDto.ClientCompanyDto.builder()
                .name(clientCompany.getName())
                .tel(clientCompany.getTel())
                .salesRepresentativeDto(toSalesRepresentativeDto(clientCompany.getSalesRepresentative()))
                .taskCount(clientCompany.getClientCompanySummary().getTaskCount())
                .businessCount(clientCompany.getClientCompanySummary().getBusinessCount())
                .build();
    }
}
