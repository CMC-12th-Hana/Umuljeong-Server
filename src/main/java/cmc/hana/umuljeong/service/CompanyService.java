package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;

public interface CompanyService {
    Company create(CompanyRequestDto.CompanyCreateDto request);
}
