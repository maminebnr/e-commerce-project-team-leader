package tn.clevory.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.clevory.api.dto.CreateProductRequest;
import tn.clevory.api.dto.ProductDto;
import tn.clevory.api.dto.ProductPageDto;
import tn.clevory.api.service.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Opérations sur les produits")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Liste paginée et filtrée des produits", operationId = "listProducts")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Page de produits"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ProductPageDto list(
            @Parameter(description = "Recherche textuelle sur le nom")
            @RequestParam(required = false) String q,
            @Parameter(description = "Filtrer par catégorie")
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Prix minimum")
            @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Prix maximum")
            @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Pagination Spring Data (page, size, sort)")
            @PageableDefault(size = 20) Pageable pageable) {
        return service.list(q, categoryId, minPrice, maxPrice, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Détail d'un produit", operationId = "getProduct")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit trouvé"),
            @ApiResponse(responseCode = "404", description = "Produit introuvable",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ProductDto get(
            @Parameter(description = "Identifiant du produit", required = true)
            @PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @Operation(summary = "Créer un produit", operationId = "createProduct")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produit créé"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ProductDto> create(@Valid @RequestBody CreateProductRequest request) {
        ProductDto created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un produit", operationId = "updateProduct")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit mis à jour"),
            @ApiResponse(responseCode = "404", description = "Produit introuvable",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ProductDto update(
            @PathVariable Long id,
            @Valid @RequestBody CreateProductRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit", operationId = "deleteProduct")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produit supprimé"),
            @ApiResponse(responseCode = "404", description = "Produit introuvable",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
