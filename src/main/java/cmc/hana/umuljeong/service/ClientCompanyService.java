package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;

public interface ClientCompanyService {

    ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request);
}
