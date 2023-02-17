package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.repository.ClientCompanyRepository;
import cmc.hana.umuljeong.service.ClientCompanyService;
import cmc.hana.umuljeong.web.dto.ClientCompanyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClientCompanyServiceImpl implements ClientCompanyService {

    private final ClientCompanyRepository clientCompanyRepository;

    @Transactional
    @Override
    public ClientCompany create(ClientCompanyRequestDto.CreateClientCompanyDto request) {
        return null;
    }

    @Override
    public ClientCompany findById(Long clientCompanyId) {
        return clientCompanyRepository.findById(clientCompanyId).get();
    }
}