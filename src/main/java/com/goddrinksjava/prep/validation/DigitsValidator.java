package com.goddrinksjava.prep.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DigitsValidator implements ConstraintValidator<Digits, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.chars().allMatch(Character::isDigit);
    }
}
