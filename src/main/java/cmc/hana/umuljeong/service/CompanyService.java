package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;

public interface CompanyService {
    Company create(Member member, CompanyRequestDto.CompanyCreateDto request);
}
