package com.kueshi.api_my_finances.user.dto;

import com.kueshi.api_my_finances.user.annotations.ValidCPF;
import com.kueshi.api_my_finances.user.annotations.ValidPassword;
import com.kueshi.api_my_finances.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDTO(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "password cannot be blank")
        @ValidPassword
        String password,

        @NotBlank(message = "FullName cannot be blank")
        String fullName,

        @NotBlank(message = "email cannot be blank")
        @Email(message = "email inválido")
        String email,

        @NotBlank(message = "cpf cannot be blank")
        @ValidCPF
        String cpf,

        @NotNull(message = "role cannot be blank")
        UserRole role
) {
}
