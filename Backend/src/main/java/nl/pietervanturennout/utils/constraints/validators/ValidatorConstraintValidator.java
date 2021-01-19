package nl.pietervanturennout.utils.constraints.validators;

import nl.pietervanturennout.utils.constraints.Validator;
import nl.pietervanturennout.utils.constraints.validation.ValidatorResult;
import nl.pietervanturennout.utils.constraints.validation.Validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorConstraintValidator implements ConstraintValidator<Validator, String> {
    private nl.pietervanturennout.utils.constraints.validation.Validator validator;

    @Override
    public void initialize(Validator constraintAnnotation) {
        validator = Validators.fromKey(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validator.test(value) == ValidatorResult.ok;
    }
}
