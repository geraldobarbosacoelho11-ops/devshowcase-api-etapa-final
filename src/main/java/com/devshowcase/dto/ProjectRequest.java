package com.devshowcase.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ProjectRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
        String title,

        @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
        String description,

        @Pattern(regexp = "^$|https?://.+", message = "Repository URL deve ser uma URL válida iniciada por http:// ou https://")
        String repositoryUrl,

        @Pattern(regexp = "^$|https?://.+", message = "Demo URL deve ser uma URL válida iniciada por http:// ou https://")
        String demoUrl,

        @NotNull(message = "profileId é obrigatório")
        Long profileId,

        Set<Long> technologyIds
) {}
