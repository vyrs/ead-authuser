package com.ead.authuser.validation

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class UsernameConstraintImpl: ConstraintValidator<UsernameConstraint, String> {

    override fun isValid(username: String?, p1: ConstraintValidatorContext?): Boolean {
        if(username == null || username.trim().isEmpty() || username.contains(" ")){
            return false;
        }
        return true;
    }

}
