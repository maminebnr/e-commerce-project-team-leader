# Cheat Sheet — Naming & Design API REST

## Paths

| Règle | Exemple correct | À éviter |
|-------|-----------------|----------|
| Pluriel | `/products` | `/product` |
| kebab-case | `/order-items` | `/orderItems`, `/order_items` |
| Nested (max 2 niveaux) | `/orders/{id}/items` | `/customers/{id}/orders/{oid}/items/{iid}` |
| Pas de verbe | `DELETE /products/{id}` | `POST /products/delete/{id}` |
| Actions métier | `POST /orders/{id}/cancel` | `PUT /orders/{id}/status=cancelled` |

## Query params

| Usage | Exemple |
|-------|---------|
| Pagination | `?page=0&size=20` |
| Tri | `?sort=price,desc` (whitelist) |
| Filtre | `?status=ACTIVE&q=iphone` |
| Include / expansion | `?include=category,stock` |
| Sparse fields | `?fields=id,name,price` |

## Codes statut

| Code | Usage |
|------|-------|
| 200 | GET / PUT / PATCH réussi |
| 201 | POST création |
| 204 | DELETE réussi (pas de body) |
| 400 | Validation / requête invalide |
| 401 | Non authentifié |
| 403 | Non autorisé |
| 404 | Ressource absente |
| 409 | Conflit (ex: email déjà pris) |
| 422 | Entité non processable |
| 429 | Rate limit |

## DTOs

- **Input** : `CreateProductRequest`, `UpdateProductRequest`
- **Output** : `ProductDto`, `ProductSummary` (projection)
- Records Java + `@NotNull` / `@Size` / `@Positive`
- MapStruct pour Entité ↔ DTO
