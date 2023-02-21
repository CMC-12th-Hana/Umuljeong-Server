package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;

import java.util.List;

public interface ClientCompanyService {

    ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request, Member member);

    ClientCompany findById(Long clientCompanyId);

    List<ClientCompany> findByCompany(Company company);

    ClientCompany update(Long clientCompanyId, ClientCompanyRequestDto.UpdateClientCompanyDto request, Member member);
}
