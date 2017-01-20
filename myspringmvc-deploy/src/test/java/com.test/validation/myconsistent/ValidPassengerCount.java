package com.test.validation.myconsistent;

import com.test.validation.Car;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidPassengerCountValidator1.class})
@Documented
public @interface ValidPassengerCount {
    String message() default "{org.hibernate.validator.referenceguide.chapter06.classlevel." +
            "ValidPassengerCount.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class ValidPassengerCountValidator1
        implements ConstraintValidator<ValidPassengerCount, Car> {
    public void initialize(ValidPassengerCount constraintAnnotation) {
    }

    public boolean isValid(Car car, ConstraintValidatorContext context) {
        if (car == null) {
            return true;
        }
        return car.getPassengers().size() <= car.getSeatCount();
    }
}