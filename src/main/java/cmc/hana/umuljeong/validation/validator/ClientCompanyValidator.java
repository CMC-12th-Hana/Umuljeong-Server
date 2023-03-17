package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Member;

public class ClientCompanyValidator {

    public static boolean isAccessible(Member member, Long clientCompanyId) {
        return member.getCompany().getClientCompanyList().stream().anyMatch(clientCompany -> clientCompany.getId().equals(clientCompanyId));
    }
}
