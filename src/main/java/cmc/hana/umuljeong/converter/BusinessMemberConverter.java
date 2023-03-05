package cmc.hana.umuljeong.converter;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.mapping.BusinessMember;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.exception.MemberException;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class BusinessMemberConverter {

    private final BusinessRepository businessRepository;
    private final MemberRepository memberRepository;

    private static BusinessRepository staticBusinessRepository;
    private static MemberRepository staticMemberRepository;

    @PostConstruct
    void init() {
        this.staticBusinessRepository = this.businessRepository;
        this.staticMemberRepository = this.memberRepository;
    }

    public static BusinessMember toBusinessMember(Business business, Long memberId) {
        return BusinessMember.builder()
                .business(business)
                .member(staticMemberRepository.findById(memberId).orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND)))
                .build();
    }


}
