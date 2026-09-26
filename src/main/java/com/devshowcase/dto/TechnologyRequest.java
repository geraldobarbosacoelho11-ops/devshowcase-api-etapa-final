package com.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TechnologyRequest(
        @NotBlank(message = "Nome da tecnologia é obrigatório")
        @Size(max = 80, message = "Nome da tecnologia deve ter no máximo 80 caracteres")
        String name
) {}
