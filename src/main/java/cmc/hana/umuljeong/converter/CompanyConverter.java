package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.web.dto.CompanyResponseDto;

public class CompanyConverter {

    public static CompanyResponseDto.CompanyCreateDto toCompanyCreateDto(Company company) {
        return CompanyResponseDto.CompanyCreateDto.builder()
                .companyId(company.getId())
                .createdAt(company.getCreatedAt())
                .build();
    }
}
