package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.dto.AuthRequestDto;

public interface MemberService {
    Member join(AuthRequestDto.JoinDto joinDto);
}
