package cmc.hana.umuljeong.service.impl;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.repository.mapping.BusinessMemberRepository;
import cmc.hana.umuljeong.service.BusinessMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessMemberServiceImpl implements BusinessMemberService {

    private final BusinessMemberRepository businessMemberRepository;

    @Override
    public List<BusinessMember> findByBusiness(Business business) {
        return businessMemberRepository.findByBusiness(business);
    }
}
