package com.kueshi.api_my_finances.cards.dto;

import jakarta.validation.constraints.NotBlank;

public record CardCreateDTO(
        @NotBlank(message = "Icon não pode ser vazio")
        String name,

        @NotBlank(message = "Icon não pode ser vazio")
        String icon
) {
}
