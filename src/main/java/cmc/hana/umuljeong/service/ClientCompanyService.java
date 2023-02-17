package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;

import java.util.List;

public interface ClientCompanyService {

    ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request);

    ClientCompany findById(Long clientCompanyId);

    List<ClientCompany> findByCompany(Company company);
}
