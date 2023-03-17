package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Member;

public class CompanyValidator {
    public static boolean isAccessible(Member member, Long companyId) {
        return companyId.equals(member.getCompany().getId());
    }

    public static boolean existsByMember(Member member) {
        return member.getCompany() != null;
    }
}
