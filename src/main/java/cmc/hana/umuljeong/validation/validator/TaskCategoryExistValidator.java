package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.exception.common.ErrorCode;
import cmc.hana.umuljeong.repository.TaskCategoryRepository;
import cmc.hana.umuljeong.validation.annotation.ExistTaskCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class TaskCategoryExistValidator implements ConstraintValidator<ExistTaskCategory, Long> {

    private final TaskCategoryRepository taskCategoryRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(!taskCategoryRepository.existsById(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.TASK_CATEGORY_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ExistTaskCategory constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
