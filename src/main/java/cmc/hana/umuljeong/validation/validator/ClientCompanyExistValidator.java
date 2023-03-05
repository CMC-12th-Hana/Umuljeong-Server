package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.ClientCompanyRepository;
import cmc.hana.umuljeong.validation.annotation.ExistClientCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class ClientCompanyExistValidator implements ConstraintValidator<ExistClientCompany, Long> {

    private final ClientCompanyRepository clientCompanyRepository;
    
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(!clientCompanyRepository.existsById(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.CLIENT_COMPANY_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ExistClientCompany constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
