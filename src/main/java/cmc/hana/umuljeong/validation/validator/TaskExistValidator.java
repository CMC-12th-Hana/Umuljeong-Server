package cmc.hana.umuljeong.validation.validator;

import cmc.hana.umuljeong.exception.ErrorCode;
import cmc.hana.umuljeong.repository.TaskRepository;
import cmc.hana.umuljeong.validation.annotation.ExistTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class TaskExistValidator implements ConstraintValidator<ExistTask, Long> {

    private final TaskRepository taskRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if(!taskRepository.existsById(value)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorCode.TASK_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }

    @Override
    public void initialize(ExistTask constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
