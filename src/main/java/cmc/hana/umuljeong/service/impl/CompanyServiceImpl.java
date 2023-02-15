package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.service.CompanyService;
import cmc.hana.umuljeong.web.dto.CompanyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public Company create(CompanyRequestDto.CompanyCreateDto request) {
        return null;
    }
}
