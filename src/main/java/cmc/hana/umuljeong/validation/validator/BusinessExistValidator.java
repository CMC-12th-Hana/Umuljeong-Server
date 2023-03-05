package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.BusinessRepository;
import cmc.hana.umuljeong.validation.annotation.ExistBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class BusinessExistValidator implements ConstraintValidator<ExistBusiness, Long> {

    private final BusinessRepository businessRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(!businessRepository.existsById(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.BUSINESS_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ExistBusiness constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}