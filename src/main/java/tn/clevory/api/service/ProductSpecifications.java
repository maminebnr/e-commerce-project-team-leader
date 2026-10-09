package tn.clevory.api.service;

import org.springframework.data.jpa.domain.Specification;
import tn.clevory.api.entity.Product;

import java.math.BigDecimal;

/**
 * Specifications pour filtrage dynamique (J2).
 */
public final class ProductSpecifications {

    private ProductSpecifications() {}

    public static Specification<Product> hasNameContaining(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%");
        };
    }

    public static Specification<Product> hasCategoryId(Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) return cb.conjunction();
            return cb.equal(root.get("categoryId"), categoryId);
        };
    }

    public static Specification<Product> priceGreaterThanOrEqual(BigDecimal min) {
        return (root, query, cb) -> {
            if (min == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("price"), min);
        };
    }

    public static Specification<Product> priceLessThanOrEqual(BigDecimal max) {
        return (root, query, cb) -> {
            if (max == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("price"), max);
        };
    }
}
