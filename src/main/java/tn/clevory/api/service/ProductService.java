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
import tn.clevory.api.mapper.ProductMapper;
import tn.clevory.api.repository.CategoryRepository;
import tn.clevory.api.repository.ProductRepository;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository,
                          CategoryRepository categoryRepository,
                          ProductMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public ProductPageDto list(String q, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        Specification<Product> spec = Specification
                .where(ProductSpecifications.hasNameContaining(q))
                .and(ProductSpecifications.hasCategoryId(categoryId))
                .and(ProductSpecifications.priceGreaterThanOrEqual(minPrice))
                .and(ProductSpecifications.priceLessThanOrEqual(maxPrice));

        Page<Product> page = repository.findAll(spec, pageable);
        return new ProductPageDto(
                page.map(mapper::toDto).getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public ProductDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
    }

    @Transactional
    public ProductDto create(CreateProductRequest request) {
        Product entity = mapper.toEntity(request);
        categoryRepository.findById(request.categoryId())
                .ifPresent(c -> entity.setCategoryName(c.getName()));
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public ProductDto update(Long id, CreateProductRequest request) {
        Product entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
        mapper.updateEntityFromRequest(request, entity);
        categoryRepository.findById(request.categoryId())
                .ifPresent(c -> entity.setCategoryName(c.getName()));
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product with id " + id + " not found");
        }
        repository.deleteById(id);
    }
}
