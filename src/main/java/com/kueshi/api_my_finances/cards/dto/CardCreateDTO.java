package com.kueshi.api_my_finances.cards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CardCreateDTO(
        @NotBlank(message = "name não pode ser vazio")
        String name,

        @NotBlank(message = "Icon não pode ser vazio")
        @Pattern(
                regexp = "(?i)^.*\\.(jpg|jpeg|png)$",
                message = "O ícone deve ser um arquivo JPG ou PNG"
        )
        String icon
) {
}
