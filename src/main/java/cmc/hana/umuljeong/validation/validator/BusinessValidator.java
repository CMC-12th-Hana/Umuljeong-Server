package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;

public class BusinessValidator {

    public static boolean isAccessible(Member member, Long businessId) {
        boolean isValid = false;
        for(ClientCompany clientCompany : member.getCompany().getClientCompanyList()) {
            isValid = clientCompany.getBusinessList().stream().anyMatch(business -> business.getId().equals(businessId));

            if(isValid) break;
        }

        return isValid;
    }
}
