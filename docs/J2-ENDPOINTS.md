# J2 — Design des endpoints

Branche de référence : `day2/endpoints-design`.  
On part de `day1/spectral-rules`.

Un seul `GET /products`. Le path est un nom. La page et les filtres sont des query params.

---

## 1. Naming

**Fichiers :** `docs/CHEATSHEET-NAMING.md`, `.spectral.yml`

| On écrit | On n'écrit pas |
|---|---|
| `/products` | `/product`, `/getAllProducts` |
| `/order-items` | `/orderItems` |
| `DELETE /products/42` | `POST /products/delete/42` |
| `listProducts` | `list-products` |

Dans `.spectral.yml`, `path-kebab-case` et `operation-id-camel-case` sont en **error**. La CI bloque si Spectral échoue.

```bash
spectral lint openapi/**/*.yaml --ruleset .spectral.yml
```

Le path nomme la ressource. Le verbe HTTP porte l'action.

---

## 2. Les URLs

**Fichier :** `openapi/products-v1.yaml`

| Path | Verbes |
|---|---|
| `/products` | GET, POST |
| `/products/{id}` | GET, PUT, DELETE |

Deux niveaux maximum : `/orders/{orderId}/items`.  
Un filtre n'est pas un nouveau path : `GET /products?categoryId=1`.

---

## 3. Entité Category

**Fichier :** `src/main/java/tn/clevory/api/entity/Category.java`

```java
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String name;

    @Column(length = 255)
    private String description;
}
```

Remplace le faux nom `Category-1`. Hibernate crée la table (`ddl-auto: update`).

---

## 4. CategoryRepository

**Fichier :** `src/main/java/tn/clevory/api/repository/CategoryRepository.java`

```java
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
```

`findById` est déjà fourni. On n'écrit pas de SQL.

---

## 5. Données de démo

**Fichier :** `src/main/resources/data.sql`  
Chargé par `application.yml` (`data-locations: classpath:data.sql`), après la création des tables.

```sql
INSERT INTO categories (name, description) VALUES
('Smartphones', 'Téléphones mobiles'),
('Laptops', 'Ordinateurs portables'),
('Audio', 'Écouteurs et enceintes');
```

À mettre **avant** les produits. Les ids deviennent 1, 2, 3.  
iPhone et Galaxy → 1, MacBook et Dell → 2, AirPods → 3.

---

## 6. Le vrai nom de catégorie

**Fichier :** `src/main/java/tn/clevory/api/service/ProductService.java`  
Dans `create` et dans `update`, à la place de `"Category-" + id` :

```java
categoryRepository.findById(request.getCategoryId())
        .ifPresent(c -> entity.setCategoryName(c.getName()));
```

`categoryId: 1` donne `categoryName: "Smartphones"`. Si l'id n'existe pas, on ne plante pas.

---

## 7. Pagination

**Fichiers :** `ProductController.java`, `dto/ProductPageDto.java`

```java
@PageableDefault(size = 20) Pageable pageable
```

| URL | Sens | Défaut |
|---|---|---|
| `page` | numéro, commence à 0 | 0 |
| `size` | taille de la page | 20 |
| `sort` | `price,desc` | aucun |

Réponse `ProductPageDto` : `content`, `page`, `size`, `totalElements`, `totalPages`.

```bash
curl "http://localhost:8080/products?page=0&size=2&sort=price,asc"
```

5 produits, `size=2` → `totalPages: 3`. La page suivante est `page=1`.

---

## 8. Filtres

**Fichier :** `ProductController.java`, méthode `list`

```java
@RequestParam(required = false) String q,
@RequestParam(required = false) Long categoryId,
@RequestParam(required = false) BigDecimal minPrice,
@RequestParam(required = false) BigDecimal maxPrice,
```

| Param | Effet |
|---|---|
| `q` | le nom contient ce texte |
| `categoryId` | une seule catégorie |
| `minPrice` | `price >= minPrice` |
| `maxPrice` | `price <= maxPrice` |

`required = false` : sans ça, `GET /products` seul répond 400.  
Les filtres se cumulent avec `page`, `size` et `sort`.

```bash
curl "http://localhost:8080/products?q=pro&categoryId=1&minPrice=500&maxPrice=1500&sort=price,desc"
```

---

## 9. ProductRepository

**Fichier :** `src/main/java/tn/clevory/api/repository/ProductRepository.java`

```java
public interface ProductRepository
        extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
```

`JpaSpecificationExecutor` apporte `findAll(Specification, Pageable)` : WHERE dynamique + page.

---

## 10. Un prédicat par filtre

**Fichier :** `src/main/java/tn/clevory/api/service/ProductSpecifications.java`

```java
public static Specification<Product> hasNameContaining(String q) {
    return (root, query, cb) -> {
        if (q == null || q.isBlank()) return cb.conjunction();
        return cb.like(cb.lower(root.get("name")), "%" + q.toLowerCase() + "%");
    };
}
```

Paramètre absent → `cb.conjunction()` → le filtre ne retire rien.

| Méthode | SQL |
|---|---|
| `hasCategoryId` | `category_id = ?` |
| `priceGreaterThanOrEqual` | `price >= ?` |
| `priceLessThanOrEqual` | `price <= ?` |

---

## 11. Assembler la page

**Fichier :** `ProductService.java`, méthode `list`

```java
Specification<Product> spec = Specification
        .where(ProductSpecifications.hasNameContaining(q))
        .and(ProductSpecifications.hasCategoryId(categoryId))
        .and(ProductSpecifications.priceGreaterThanOrEqual(minPrice))
        .and(ProductSpecifications.priceLessThanOrEqual(maxPrice));

Page<Product> page = repository.findAll(spec, pageable);

return new ProductPageDto(
        page.map(this::toDto).getContent(),
        page.getNumber(), page.getSize(),
        page.getTotalElements(), page.getTotalPages());
```

Le `WHERE` et le `LIMIT` sont faits en SQL (`show-sql: true` dans `application.yml`).

---

## 12. Vérifier

```bash
./mvnw spring-boot:run
```

| Appel | Résultat |
|---|---|
| `GET /products?size=2` | 2 produits, `totalElements: 5`, `totalPages: 3` |
| `GET /products?categoryId=2` | MacBook et Dell, `categoryName: Laptops` |
| `GET /products?q=pro` | iPhone 16 Pro et MacBook Pro 14 |
| `GET /products?minPrice=1000&maxPrice=1500&sort=price,asc` | Galaxy 1099, iPhone 1229, Dell 1499 |

Swagger : http://localhost:8080/swagger-ui.html
