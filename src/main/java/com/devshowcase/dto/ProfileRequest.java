package com.devshowcase.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProfileRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 120, message = "Nome deve ter no máximo 120 caracteres")
        String name,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @Size(max = 1000, message = "Bio deve ter no máximo 1000 caracteres")
        String bio,

        @Pattern(regexp = "^$|https?://.+", message = "GitHub deve ser uma URL válida iniciada por http:// ou https://")
        String githubUrl,

        @Pattern(regexp = "^$|https?://.+", message = "LinkedIn deve ser uma URL válida iniciada por http:// ou https://")
        String linkedinUrl
) {}
