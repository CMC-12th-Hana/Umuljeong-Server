package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Company;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;

import java.util.List;

public interface MemberService {
    Member join(AuthRequestDto.JoinDto joinDto);

    Member create(Long companyId, MemberRequestDto.CreateDto request);

    List<Member> findByCompanyAndName(Long companyId, String name);

    Member joinCompany(Member member);

    Member updateProfile(Member member, MemberRequestDto.UpdateProfileDto request);

    Member findById(Long memberId);

    void delete(Long memberId);

    Member updatePassword(Member member, MemberRequestDto.UpdatePasswordDto request);

    Member updateMemberProfile(Long memberId, MemberRequestDto.UpdateMemberProfileDto request);

    Member updateMemberRole(Member leader, Long memberId);
}
