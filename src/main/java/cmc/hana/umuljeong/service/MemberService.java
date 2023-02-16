package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;
import cmc.hana.umuljeong.web.dto.MemberRequestDto;

public interface MemberService {
    Member join(AuthRequestDto.JoinDto joinDto);

    Member create(Member leader, MemberRequestDto.CreateDto request);
}
