package com.goddrinksjava.prep.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TrimmedValidator implements ConstraintValidator<Trimmed, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.trim().equals(value);
    }
}
