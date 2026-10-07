package tn.clevory.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO d'entrée — Record + Bean Validation (J2).
 */
public record CreateProductRequest(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(min = 2, max = 120, message = "Le nom doit contenir entre 2 et 120 caractères")
        String name,

        @NotNull(message = "Le prix est obligatoire")
        @Positive(message = "Le prix doit être strictement positif")
        @Digits(integer = 10, fraction = 2)
        BigDecimal price,

        @NotNull(message = "La catégorie est obligatoire")
        Long categoryId,

        @Size(max = 2000, message = "La description ne peut dépasser 2000 caractères")
        String description
) {}
