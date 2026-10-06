# Guide Spectral — Formation Clevory

## Installation

```bash
npm install -g @stoplight/spectral-cli
```

## Utilisation locale

```bash
# Lint tous les fichiers OpenAPI
spectral lint openapi/**/*.yaml

# Avec le ruleset équipe
spectral lint openapi/**/*.yaml --ruleset .spectral.yml

# Format JSON (pour CI)
spectral lint openapi/**/*.yaml -f json
```

## Règles custom équipe

| Règle | Severity | Description |
|-------|----------|-------------|
| `path-kebab-case` | error | Paths en kebab-case uniquement |
| `operation-id-camel-case` | error | operationId en camelCase |
| `must-document-pagination` | warn | GET collection → documenter page/size |

## Workflow recommandé

1. Modifier `openapi/*.yaml`
2. `spectral lint` → corriger les **errors**
3. Commit + push → CI rejoue Spectral
4. PR bloquée si error > 0

## Exemples d'erreurs fréquentes

```
# ❌ path camelCase
/getAllProducts

# ✅
/products

# ❌ operationId avec tiret
list-products

# ✅
listProducts
```

## Ownership

- **API Champion** de la squad = responsable du contrat + Spectral
- Team Lead valide en design review (checklist)
