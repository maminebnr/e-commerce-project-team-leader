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
| `path-kebab-case` | error | Paths en kebab-case, paramètres en {camelCase} |
| `operation-id-camel-case` | error | operationId en camelCase |
| `operation-4xx-response` | error | Au moins une réponse 4xx par opération |
| `list-endpoints-paginated` | error | GET collection → paramètres page et size |
| `page-size-max-100` | error | size plafonné à 100 |
| `errors-problem-json` | error | Erreurs en application/problem+json |
| `post-returns-201` / `delete-returns-204` | warn | Codes de succès REST |
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
