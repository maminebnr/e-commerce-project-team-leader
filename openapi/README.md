# Contrats OpenAPI

Placez ici les spécifications OpenAPI 3.x.

## Convention

- Un fichier par domaine / version : `products-v1.yaml`, `orders-v1.yaml`
- Ou un fichier unique `openapi.yaml` qui référence les composants

## Workflow

1. Écrire / modifier le YAML
2. `spectral lint openapi/**/*.yaml`
3. Review design
4. Générer mock : `prism mock openapi/products-v1.yaml`
5. Implémenter le backend

Les fichiers concrets apparaissent à partir de la branche `day1/rest-api-first`.
