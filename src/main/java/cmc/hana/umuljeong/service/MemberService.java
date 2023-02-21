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

    List<Member> findByCompany(Long companyId);
}
