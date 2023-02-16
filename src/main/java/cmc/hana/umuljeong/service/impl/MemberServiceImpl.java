package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.converter.MemberConverter;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.repository.MemberRepository;
import cmc.hana.umuljeong.service.MemberService;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public Member join(AuthRequestDto.JoinDto joinDto) {
        Member member = MemberConverter.toMember(joinDto);
        return memberRepository.save(member);
    }

    @Transactional
    @Override
    public Member create(Member leader, MemberRequestDto.CreateDto request) {
        Member member = MemberConverter.toMember(request);
        member.setCompany(leader.getCompany());
        return memberRepository.save(member);
    }
}
