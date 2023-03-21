package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.domain.Business;
import cmc.hana.umuljeong.domain.ClientCompany;
import cmc.hana.umuljeong.domain.Member;
import cmc.hana.umuljeong.exception.BusinessException;
import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class BusinessValidator {

    private final BusinessRepository businessRepository;
    private static BusinessRepository staticBusinessRepository;

    @PostConstruct
    public void init() {
        this.staticBusinessRepository = this.businessRepository;
    }

    public static boolean isAccessible(Member member, Long businessId) {
        boolean isValid = false;
        for(ClientCompany clientCompany : member.getCompany().getClientCompanyList()) {
            isValid = clientCompany.getBusinessList().stream().anyMatch(business -> business.getId().equals(businessId));

            if(isValid) break;
        }

        return isValid;
    }

    public static boolean isEtcBusiness(Long businessId) {
        Business business = staticBusinessRepository.findById(businessId).orElseThrow(() -> new BusinessException(ErrorCode.BUSINESS_NOT_FOUND));
        if(business.getName().equals("기타")) return true;
        return false;
    }
}
