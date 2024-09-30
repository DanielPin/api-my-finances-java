package com.kueshi.api_my_finances.user.dto;

import com.kueshi.api_my_finances.user.annotations.ValidPassword;
import com.kueshi.api_my_finances.user.enums.UserRole;
import jakarta.validation.constraints.Email;

public record UserUpdateDTO(
        String fullName,

        @Email(message = "email inválido")
        String email,

        UserRole role
) {
}
