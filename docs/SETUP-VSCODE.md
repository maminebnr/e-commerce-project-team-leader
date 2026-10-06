# Setup VS Code — Formation API Spring Boot

## 1. Prérequis

- JDK 17+
- Maven 3.9+ (ou utiliser `./mvnw`)
- Node.js 20+ (Spectral + Prism)
- VS Code

```bash
# Outils OpenAPI
npm install -g @stoplight/spectral-cli @stoplight/prism-cli
```

## 2. Ouvrir le projet

```bash
code api-training-ecommerce
```

VS Code proposera d’installer les extensions recommandées (`.vscode/extensions.json`).

## 3. Extensions clés

| Extension | Utilité |
|-----------|---------|
| Extension Pack for Java | Java + Maven + Debugger |
| Spring Boot Extension Pack | Run/Debug Spring, Dashboard |
| OpenAPI (Swagger) Editor | Édition des YAML |
| Spectral | Lint OpenAPI dans l’éditeur |
| REST Client | Fichier `requests.http` |
| GitLens | Branches day1/, day2/ |

## 4. Lancer l’application

1. Ouvrir `ApiTrainingEcommerceApplication.java`
2. Cliquer **Run** au-dessus de `main`  
   **ou** F5 (configuration dans `.vscode/launch.json`)
3. Swagger UI → http://localhost:8088/swagger-ui.html
4. H2 Console → http://localhost:8088/h2-console  
   (JDBC URL : `jdbc:h2:mem:ecommerce`)

## 5. Tester les APIs

Ouvrir `requests.http` → cliquer **Send Request** au-dessus de chaque requête.

## 6. Workflow branches

```bash
git checkout day1/rest-api-first      # TP1
git checkout day1/spectral-rules     # Spectral
git checkout day2/endpoints-design   # Filtering
git checkout day2/dtos-mapstruct     # Records + MapStruct (cible J2)
```

## 7. Commandes utiles

```bash
./mvnw spring-boot:run
spectral lint openapi/**/*.yaml --ruleset .spectral.yml
prism mock openapi/products-v1.yaml -p 4010
```

## 8. Problèmes fréquents

| Erreur | Solution |
|--------|----------|
| Table not found | `defer-datasource-initialization: true` |
| Port already in use | Changer `server.port` |
| MapStruct not found | Rebuild Maven (`Java: Clean Java Language Server Workspace`) |
| Swagger vide | Vérifier que l’app a bien démarré + package scan |
