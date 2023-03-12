package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;

import java.util.List;

public interface ClientCompanyService {

    ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request, Long companyId);

    ClientCompany findById(Long clientCompanyId);

    List<ClientCompany> findByCompanyAndName(Long companyId, String name, String sort, String orde);

    ClientCompany update(Long clientCompanyId, ClientCompanyRequestDto.UpdateClientCompanyDto request, Member member);

    void delete(Long clientCompanyId);
}
