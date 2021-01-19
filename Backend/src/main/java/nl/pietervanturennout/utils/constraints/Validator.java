package nl.pietervanturennout.utils.constraints;

import nl.pietervanturennout.utils.constraints.validators.ValidatorConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidatorConstraintValidator.class)
@Target({ METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface Validator {
    String message() default "{nl.pietervanturennout.utils.constraints.validation.Validator.message}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String value();
}
