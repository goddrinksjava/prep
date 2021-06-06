package com.goddrinksjava.prep.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphaValidator implements ConstraintValidator<Alpha, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.chars().allMatch(chara -> Character.isLetter(chara) || Character.isWhitespace(chara))
                && value.chars().anyMatch(Character::isLetter);
    }
}
