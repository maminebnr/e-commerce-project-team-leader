package tn.clevory.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tn.clevory.api.dto.CreateProductRequest;
import tn.clevory.api.dto.ProductDto;
import tn.clevory.api.dto.ProductPageDto;
import tn.clevory.api.entity.Product;
import tn.clevory.api.repository.CategoryRepository;
import tn.clevory.api.repository.ProductRepository;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public ProductPageDto list(String q, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification
                .where(ProductSpecifications.hasNameContaining(q))
                .and(ProductSpecifications.hasCategoryId(categoryId))
                .and(ProductSpecifications.priceGreaterThanOrEqual(minPrice))
                .and(ProductSpecifications.priceLessThanOrEqual(maxPrice));

        Page<Product> page = repository.findAll(spec, pageable);
        return new ProductPageDto(
                page.map(this::toDto).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public ProductDto getById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
    }

    @Transactional
    public ProductDto create(CreateProductRequest request) {
        Product entity = new Product(
                request.getName(),
                request.getPrice(),
                request.getCategoryId(),
                request.getDescription()
        );
        categoryRepository.findById(request.getCategoryId())
                .ifPresent(c -> entity.setCategoryName(c.getName()));
        return toDto(repository.save(entity));
    }

    @Transactional
    public ProductDto update(Long id, CreateProductRequest request) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
        entity.setName(request.getName());
        entity.setPrice(request.getPrice());
        entity.setCategoryId(request.getCategoryId());
        entity.setDescription(request.getDescription());
        categoryRepository.findById(request.getCategoryId())
                .ifPresent(c -> entity.setCategoryName(c.getName()));
        return toDto(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    private ProductDto toDto(Product p) {
        return new ProductDto(
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDescription(),
                p.getCategoryId(),
                p.getCategoryName(),
                p.getCreatedAt()
        );
    }
}
