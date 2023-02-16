package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;
import cmc.hana.umuljeong.web.dto.CompanyResponseDto;

public class CompanyConverter {

    public static CompanyResponseDto.CompanyCreateDto toCompanyCreateDto(Company company) {
        return CompanyResponseDto.CompanyCreateDto.builder()
                .companyId(company.getId())
                .createdAt(company.getCreatedAt())
                .build();
    }

    public static Company toCompany(CompanyRequestDto.CompanyCreateDto companyCreateDto) {
        return Company.builder()
                .name(companyCreateDto.getName())
                .build();
    }
}
