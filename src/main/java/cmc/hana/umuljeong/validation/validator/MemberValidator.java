package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Member;

public class MemberValidator {
    public static boolean isAccessible(Member member, Long memberId) {
        return member.getCompany().getMemberList().stream().anyMatch(member1 -> member1.getId() == memberId);
    }
}
