package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.web.controller.MemberRestController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    public static boolean isAccessible(Member member, Long memberId) {
        return member.getCompany().getMemberList().stream().anyMatch(member1 -> member1.getId().equals(memberId));
    }

    public static boolean isSameMember(Member leader, Long memberId) {
        return leader.getId().equals(memberId);
    }
}
