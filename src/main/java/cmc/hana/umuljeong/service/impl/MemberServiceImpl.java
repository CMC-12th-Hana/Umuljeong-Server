package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.enums.JoinCompanyStatus;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.repository.VerificationMessageRepository;
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
        String phoneNumber = joinDto.getPhoneNumber();
        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(phoneNumber);

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

        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(request.getPhoneNumber());

        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setCompany(company);
            member.setStaffNumber(request.getStaffNumber());
            member.setStaffRank(request.getStaffRank());
            return member;
        }
        else {
            Member member = MemberConverter.toMember(request);
            member.setCompany(company);
            return memberRepository.save(member);
        }
    }

    @Transactional
    @Override
    public Member updateProfile(Member member, MemberRequestDto.UpdateProfileDto request) {
        member.setName(request.getName());
        member.setStaffNumber(request.getStaffNumber());
        return member;
    }

    @Transactional
    @Override
    public Member updatePassword(Member member, MemberRequestDto.UpdatePasswordDto request) {
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        return member;
    }

    @Override
    public List<Member> findByCompany(Long companyId) {
        Company company = companyRepository.findById(companyId).get();
        return memberRepository.findByCompany(company);
    }

    @Override
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    @Override
    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
