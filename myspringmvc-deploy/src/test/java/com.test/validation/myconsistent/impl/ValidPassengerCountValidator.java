package com.test.validation.myconsistent.impl;

import com.test.validation.Car;
import com.test.validation.myconsistent.ValidPassengerCount;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPassengerCountValidator
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