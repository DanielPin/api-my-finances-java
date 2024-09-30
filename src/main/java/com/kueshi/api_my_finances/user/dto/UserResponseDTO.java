package com.kueshi.api_my_finances.user.dto;

import com.kueshi.api_my_finances.user.enums.UserRole;

public record UserResponseDTO(String id, String username, String fullName, String email, String cpf, UserRole role,
                              String createdAt, String updatedAt) {
}
