package tn.clevory.api.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO de sortie — version simple J1.
 * Sera remplacé par un Record Java en day2/dtos-mapstruct.
 */
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Instant createdAt;

    public ProductDto() {}

    public ProductDto(Long id, String name, BigDecimal price, String description,
                      Long categoryId, String categoryName, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
