package com.kueshi.api_my_finances.user.validators;

import com.kueshi.api_my_finances.user.annotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Senha não pode ser nula").addConstraintViolation();
            return false;
        }

        StringBuilder errorMessage = new StringBuilder();

        if (password.length() < 8) {
            errorMessage.append("Pelo menos 8 caracteres. ");
        }
        if (password.length() > 128) {
            errorMessage.append("Não mais do que 128 caracteres. ");
        }
        if (!password.chars().anyMatch(Character::isUpperCase)) {
            errorMessage.append("Pelo menos uma letra maiúscula. ");
        }
        if (!password.chars().anyMatch(Character::isLowerCase)) {
            errorMessage.append("Pelo menos uma letra minúscula. ");
        }
        if (!password.chars().anyMatch(Character::isDigit)) {
            errorMessage.append("Pelo menos um numeral. ");
        }
        if (!password.chars().anyMatch(ch -> "~!?@#$%^&*_-+()[]{}><\\/|\"'.,:;".indexOf(ch) >= 0)) {
            errorMessage.append("Pelo menos um caractere especial. ");
        }
        if (!password.chars().allMatch(ch ->
                Character.isLetterOrDigit(ch) || "~!?@#$%^&*_-+()[]{}><\\/|\"'.,:;".indexOf(ch) >= 0)) {
            errorMessage.append("Somente caracteres válidos permitidos. ");
        }
        if (password.contains(" ")) {
            errorMessage.append("Sem espaços. ");
        }
        if (!password.chars().allMatch(ch ->
                (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') ||
                        (ch >= 'А' && ch <= 'я') || (ch >= 'Ё' && ch <= 'ё') ||
                        Character.isDigit(ch) || "~!?@#$%^&*_-+()[]{}><\\/|\"'.,:;".indexOf(ch) >= 0)) {
            errorMessage.append("Somente caracteres do alfabeto latino ou cirílico. ");
        }

        if (!errorMessage.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage.toString().trim()).addConstraintViolation();
            return false;
        }

        return true;
    }
}

