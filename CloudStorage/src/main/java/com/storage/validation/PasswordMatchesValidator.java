package com.storage.validation;

import com.storage.dto.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignupRequest> {

    @Override
    public boolean isValid(SignupRequest request, ConstraintValidatorContext context) {
        if (request.getPassword() == null || request.getConfirmPassword() == null) return false;

        boolean match = request.getPassword().equals(request.getConfirmPassword());

        if (!match) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords must match")
                   .addPropertyNode("confirmPassword")
                   .addConstraintViolation();
        }

        return match;
    }
}
