package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;

public class TaskValidator {

    public static boolean isAccessible(Member member, Long taskId) {
        boolean isValid = false;
        for(ClientCompany clientCompany : member.getCompany().getClientCompanyList()) {
            for(Business business : clientCompany.getBusinessList()) {
                isValid = business.getTaskList().stream().anyMatch(task -> task.getId() == taskId);
                if(isValid) break;
            }
            if(isValid) break;
        }

        return isValid;
    }
}
