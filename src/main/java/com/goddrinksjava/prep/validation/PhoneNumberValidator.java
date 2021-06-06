package com.goddrinksjava.prep.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.length() == 10 &&
                value.chars().allMatch(chara -> chara >= '0' && chara <= '9');
    }
}
