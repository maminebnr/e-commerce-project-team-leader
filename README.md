# API Training E-commerce — Formation Clevory

**Conception d'APIs modernes et robustes**  
Spring Boot 3.4+ / Java 17+ / OpenAPI 3 / Angular 21

## Objectif

Projet fil rouge de la formation 5 jours. Chaque branche correspond à un jour (ou un module) et contient le code de référence **après** le TP du jour.

## Prérequis

- JDK 17+
- Maven 3.9+
- Node.js 20+ (pour Spectral CLI et mocks)
- Docker (optionnel, pour Testcontainers en J5)

```bash
# Installer Spectral CLI (lint OpenAPI)
npm install -g @stoplight/spectral-cli

# Installer Prism (mock serveur OpenAPI)
npm install -g @stoplight/prism-cli
```

## Structure des branches

| Branche                    | Jour | Contenu principal                                      |
|----------------------------|------|--------------------------------------------------------|
| `main`                     | —    | Baseline + README + CI Spectral + structure vide       |
| `day1/rest-api-first`      | J1   | Contrat OpenAPI + Springdoc + premier contrôleur       |
| `day1/spectral-rules`      | J1   | Règles Spectral custom + corrections lint              |
| `day2/endpoints-design`    | J2   | Naming, nested resources, pagination, filtering        |
| `day2/dtos-mapstruct`      | J2   | Records Java + MapStruct + Bean Validation             |
| `day3/security-jwt`        | J3   | Spring Security + JWT Resource Server + CORS           |
| `day4/errors-versioning`   | J4   | ProblemDetail + versioning + docs enrichies            |
| `day5/tests-performance`   | J5   | Spring Cloud Contract + Actuator + projet final        |

## Démarrage rapide (après checkout d'une branche)

```bash
# Backend
./mvnw spring-boot:run
# → Swagger UI : http://localhost:8080/swagger-ui.html
# → OpenAPI JSON : http://localhost:8080/v3/api-docs

# Lint OpenAPI (depuis la racine)
spectral lint openapi/**/*.yaml

# Mock serveur pour Angular (sans démarrer Spring)
prism mock openapi/products-v1.yaml -p 4010
```

## Workflow API-First (règle d'or)

1. **Écrire** le contrat `openapi/*.yaml`
2. **Linter** avec Spectral (`spectral lint`)
3. **Review** design (Team Lead + Front)
4. **Mock** pour Angular (`prism mock`)
5. **Implémenter** le backend qui respecte le contrat
6. **Tester** (MockMvc + Spring Cloud Contract)

## CI

Le workflow GitHub Actions (`.github/workflows/spectral.yml`) bloque la PR si Spectral retourne des **errors**.

## Formateur

Med Amine Ben Rhouma — Clevory Training  
contact@clevory.tn
