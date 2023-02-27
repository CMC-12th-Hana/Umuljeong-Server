package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public Member join(AuthRequestDto.JoinDto joinDto) {
        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(joinDto.getPhoneNumber());
        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setPassword(passwordEncoder.encode(joinDto.getPassword()));
            member.setIsEnabled(Boolean.TRUE);
            return member;
        }
        return memberRepository.save(MemberConverter.toMember(joinDto));
    }

    @Transactional
    @Override
    public Member joinCompany(Member member) {
        member.setJoinCompanyStatus(JoinCompanyStatus.JOINED);
        return member;
    }

    @Transactional
    @Override
    public Member create(Long companyId, MemberRequestDto.CreateDto request) {
        Company company = companyRepository.findById(companyId).get();
        // todo : 멤버의 전화번호로 조회해서 멤버가 먼저 가입한 경우도 예외처리

        Member member = MemberConverter.toMember(request);
        member.setCompany(company);
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member updateProfile(Member member, MemberRequestDto.UpdateProfileDto request) {
        member.setName(request.getName());
        member.setStaffNumber(request.getStaffNumber());
        return member;
    }

    @Override
    public List<Member> findByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        return memberRepository.findByCompany(company);
    }

}
