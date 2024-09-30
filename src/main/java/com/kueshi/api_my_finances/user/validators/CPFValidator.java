package com.kueshi.api_my_finances.user.validators;

import com.kueshi.api_my_finances.user.annotations.ValidCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class CPFValidator implements ConstraintValidator<ValidCPF, String> {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

    @Override
    public void initialize(ValidCPF constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        cpf = cpf.replaceAll("\\D", "");

        if (!CPF_PATTERN.matcher(cpf).matches() || cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return isCPFValid(cpf);
    }

    private boolean isCPFValid(String cpf) {
        int[] weights1 = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        String digit1 = calculateDigit(cpf.substring(0, 9), weights1);
        String digit2 = calculateDigit(cpf.substring(0, 9) + digit1, weights2);

        return cpf.equals(cpf.substring(0, 9) + digit1 + digit2);
    }

    private String calculateDigit(String str, int[] weights) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += Integer.parseInt(str.substring(i, i + 1)) * weights[i];
        }
        int mod = sum % 11;
        return (mod < 2) ? "0" : String.valueOf(11 - mod);
    }
}
