package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.web.dto.ClientCompanyResponseDto;

public class ClientCompanyConverter {

    public static ClientCompanyResponseDto.CreateClientCompany toCreateClientCompany(ClientCompany clientCompany) {
        return ClientCompanyResponseDto.CreateClientCompany.builder()
                .clientCompanyId(clientCompany.getId())
                .createdAt(clientCompany.getCreatedAt())
                .build();
    }
}
