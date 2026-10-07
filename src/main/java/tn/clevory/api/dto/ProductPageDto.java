package tn.clevory.api.dto;

import java.util.List;

/**
 * Envelope de pagination — Record (J2).
 */
public record ProductPageDto(
        List<ProductDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
