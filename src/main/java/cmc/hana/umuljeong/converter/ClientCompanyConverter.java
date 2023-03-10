package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.embedded.SalesRepresentative;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
                .clientId(clientCompany.getId())
                .name(clientCompany.getName())
                .tel(clientCompany.getTel())
                .salesRepresentativeDto(toSalesRepresentativeDto(clientCompany.getSalesRepresentative()))
                .taskCount(clientCompany.getTaskCount())
                .businessCount(clientCompany.getBusinessCount())
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

    public static ClientCompany toClientCompany(ClientCompanyRequestDto.CreateClientCompanyDto request, Company company) {
        ClientCompanyRequestDto.SalesRepresentativeDto salesRepresentativeDto = request.getSalesRepresentativeDto();

        SalesRepresentative salesRepresentative = SalesRepresentative.builder()
                .name(salesRepresentativeDto.getName() != null ? salesRepresentativeDto.getName() : "")
                .department(salesRepresentativeDto.getDepartment() != null ? salesRepresentativeDto.getDepartment() : "")
                .phoneNumber(salesRepresentativeDto.getDepartment() != null ? salesRepresentativeDto.getPhoneNumber() : "")
                .build();

        ClientCompany clientCompany = ClientCompany.builder()
                .name(request.getName())
                .tel(request.getTel())
                .salesRepresentative(salesRepresentative)
                .businessList(new ArrayList<>())
                .businessCount(0)
                .taskCount(0)
                .build();
        clientCompany.setCompany(company);

        return clientCompany;
    }

    public static ClientCompanyResponseDto.DeleteClientCompany toDeleteClientCompany() {
        return ClientCompanyResponseDto.DeleteClientCompany.builder()
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
