package com.devshowcase.dto;

import jakarta.validation.constraints.*;

public record FeedbackRequest(
        @NotNull(message = "A nota é obrigatória")
        @Min(value = 1, message = "A nota deve ser entre 1 e 5")
        @Max(value = 5, message = "A nota deve ser entre 1 e 5")
        Integer rating,

        @NotBlank(message = "O comentário é obrigatório")
        @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres")
        String comment,

        @Size(max = 120, message = "O autor deve ter no máximo 120 caracteres")
        String author
) {}
