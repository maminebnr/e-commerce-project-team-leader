package tn.clevory.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO d'entrée — version J1 avec Bean Validation.
 * Sera converti en Record en day2.
 */
public class CreateProductRequest {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 120, message = "Le nom doit contenir entre 2 et 120 caractères")
    private String name;

    @NotNull(message = "Le prix est obligatoire")
    @Positive(message = "Le prix doit être strictement positif")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "La catégorie est obligatoire")
    private Long categoryId;

    @Size(max = 2000, message = "La description ne peut dépasser 2000 caractères")
    private String description;

    public CreateProductRequest() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
