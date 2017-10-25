package com.test.validation.myconsistent;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by fanyun on 17/1/5.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {GenericValidatorBootstrapperValidator.class, ListValidatorImpl.class})
public @interface CustomAnnotation {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends ConstraintValidator<? extends CustomAnnotation, Serializable>> validator();

}

class GenericValidatorBootstrapperValidator implements ConstraintValidator<CustomAnnotation, Object> {

    private ConstraintValidator validator = null;

    public void initialize(CustomAnnotation constraintAnnotation) {
        Class<? extends ConstraintValidator> validatorClass = constraintAnnotation.validator();
        try {
            validator = validatorClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        validator.initialize(constraintAnnotation); //TODO with what?
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return validator.isValid(value, context);
    }
}

class ListValidatorImpl implements ConstraintValidator<CustomAnnotation, List> {

    public void initialize(CustomAnnotation constraintAnnotation) {
    }

    public boolean isValid(List value, ConstraintValidatorContext context) {
        return true;
    }
}

