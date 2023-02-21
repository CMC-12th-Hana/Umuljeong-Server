package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.ClientCompanySummary;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.embedded.SalesRepresentative;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;

import java.util.List;
import java.util.stream.Collectors;

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

    public static ClientCompanyResponseDto.ClientCompanyListDto toClientCompanyListDto(List<ClientCompany> clientCompanyList) {
        List<ClientCompanyResponseDto.ClientCompanyDto> clientCompanyDtoList =
                clientCompanyList.stream()
                        .map(clientCompany -> toClientCompanyDto(clientCompany))
                        .collect(Collectors.toList());

        return ClientCompanyResponseDto.ClientCompanyListDto.builder()
                .clientCompanyDtoList(clientCompanyDtoList)
                .build();
    }

    public static ClientCompanyResponseDto.UpdateClientCompany toUpdateClientCompany(ClientCompany clientCompany) {
        return ClientCompanyResponseDto.UpdateClientCompany.builder()
                .clientCompanyId(clientCompany.getId())
                .updatedAt(clientCompany.getUpdatedAt())
                .build();
    }

    public static ClientCompany toClientCompany(ClientCompanyRequestDto.CreateClientCompanyDto request, Member member) {
        ClientCompanySummary clientCompanySummary = ClientCompanySummary.builder()
                .businessCount(0)
                .taskCount(0)
                .build();

        SalesRepresentative salesRepresentative = SalesRepresentative.builder()
                .name(request.getSalesRepresentativeDto().getName())
                .department(request.getSalesRepresentativeDto().getDepartment())
                .phoneNumber(request.getSalesRepresentativeDto().getPhoneNumber())
                .build();

        return ClientCompany.builder()
                .company(member.getCompany())
                .clientCompanySummary(clientCompanySummary)
                .name(request.getName())
                .tel(request.getTel())
                .salesRepresentative(salesRepresentative)
                .build();
    }
}
