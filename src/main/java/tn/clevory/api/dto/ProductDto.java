package tn.clevory.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO de sortie — Record Java immuable (J2).
 */
public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        Long categoryId,
        String categoryName,
        Instant createdAt
) {}
