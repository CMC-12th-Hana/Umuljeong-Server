package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.exception.ErrorCode;
import cmc.hana.umuljeong.repository.CompanyRepository;
import cmc.hana.umuljeong.validation.annotation.ExistCompany;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class CompanyExistValidator implements ConstraintValidator<ExistCompany, Long> {

    private final CompanyRepository companyRepository;


    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(!companyRepository.existsById(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.COMPANY_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ExistCompany constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
