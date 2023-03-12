package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.BusinessConverter;
import cmc.hana.umuljeong.converter.ClientCompanyConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.repository.ClientCompanyRepository;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.repository.mapping.BusinessMemberRepository;
import cmc.hana.umuljeong.repository.querydsl.ClientCompanyCustomRepository;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientCompanyServiceImpl implements ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;
    private final ClientCompanyCustomRepository clientCompanyCustomRepository;
    private final CompanyRepository companyRepository;

    private final BusinessMemberRepository businessMemberRepository;
    private final BusinessRepository businessRepository;

    @Transactional
    @Override
    public ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request, Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        ClientCompany clientCompany = ClientCompanyConverter.toClientCompany(request, company);

        Business etcBusiness = BusinessConverter.toEtcBusiness(clientCompany);
        businessRepository.save(etcBusiness);

        return clientCompanyRepository.save(clientCompany);
    }


    @Transactional
    @Override
    public ClientCompany update(Long clientCompanyId, ClientCompanyRequestDto.UpdateClientCompanyDto request, Member member) {
        ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).get();
        clientCompany.setName(request.getName());
        clientCompany.setTel(request.getTel());
        clientCompany.getSalesRepresentative().setName(request.getSalesRepresentativeDto().getName());
        clientCompany.getSalesRepresentative().setDepartment(request.getSalesRepresentativeDto().getDepartment());
        clientCompany.getSalesRepresentative().setPhoneNumber(request.getSalesRepresentativeDto().getPhoneNumber());
        return clientCompany;
    }

    @Transactional
    @Override
    public void delete(Long clientCompanyId) {
        ClientCompany clientCompany = clientCompanyRepository.findById(clientCompanyId).get();
        clientCompany.removeRelationshop();
        clientCompanyRepository.delete(clientCompany);
        return ;
    }

    @Override
    public ClientCompany findById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).get();
    }

    @Override
    public List<ClientCompany> findByCompanyAndName(Long companyId, String name) {
        Company company = companyRepository.findById(companyId).get();
        return clientCompanyCustomRepository.findByCompanyAndName(company, name);
    }
}