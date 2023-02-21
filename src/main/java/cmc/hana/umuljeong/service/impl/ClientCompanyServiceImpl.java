package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.ClientCompanyConverter;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.repository.ClientCompanyRepository;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientCompanyServiceImpl implements ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;

    @Transactional
    @Override
    public ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request, Member member) {
        ClientCompany clientCompany = ClientCompanyConverter.toClientCompany(request, member);
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

    @Override
    public ClientCompany findById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).get();
    }

    @Override
    public List<ClientCompany> findByCompany(Company company) {
        return clientCompanyRepository.findByCompany(company);
    }
}