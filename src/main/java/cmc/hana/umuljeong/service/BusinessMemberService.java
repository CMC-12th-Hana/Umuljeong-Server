package cmc.hana.umuljeong.service;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;

import java.util.List;

public interface BusinessMemberService {
    List<BusinessMember> findByBusiness(Business business);
}
