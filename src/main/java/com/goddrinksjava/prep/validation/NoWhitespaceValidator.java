package com.goddrinksjava.prep.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoWhitespaceValidator implements ConstraintValidator<NoWhitespace, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.chars().noneMatch(Character::isWhitespace);
    }
}
