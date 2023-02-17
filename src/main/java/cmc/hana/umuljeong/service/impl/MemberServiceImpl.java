package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Member join(AuthRequestDto.JoinDto joinDto) {
        Optional<Member> optionalMember = memberRepository.findByPhoneNumber(joinDto.getPhoneNumber());
        if(optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setEmail(joinDto.getEmail());
            member.setPassword(passwordEncoder.encode(joinDto.getPassword()));
            member.setIsEnabled(Boolean.TRUE);
            return member;
        }
        return memberRepository.save(MemberConverter.toMember(joinDto));
    }

    @Transactional
    @Override
    public Member create(Member leader, MemberRequestDto.CreateDto request) {
        Member member = MemberConverter.toMember(request);
        member.setCompany(leader.getCompany());
        return memberRepository.save(member);
    }
}