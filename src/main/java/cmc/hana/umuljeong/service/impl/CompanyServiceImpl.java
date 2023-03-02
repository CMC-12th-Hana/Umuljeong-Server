package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.CompanyConverter;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.domain.enums.MemberRole;
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
    public Company create(Member member, CompanyRequestDto.CompanyCreateDto request) {
        Company savedCompany = companyRepository.save(CompanyConverter.toCompany(request));
        member.setCompany(savedCompany);
        member.setMemberRole(MemberRole.LEADER);
        member.setJoinCompanyStatus(JoinCompanyStatus.JOINED);
        return savedCompany;
    }
}
