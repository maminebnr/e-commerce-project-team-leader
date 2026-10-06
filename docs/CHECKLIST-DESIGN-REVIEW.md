# Checklist Design Review API — Team Lead

À utiliser avant chaque merge de branche `day*/*`.

| # | Question | OK ? |
|---|----------|------|
| 1 | Le contrat OpenAPI a-t-il été écrit **AVANT** le code ? | ☐ |
| 2 | Spectral lint passe-t-il avec **0 error** ? | ☐ |
| 3 | Les paths respectent-ils **pluriel + kebab-case** ? | ☐ |
| 4 | Les verbes HTTP sont-ils sémantiquement corrects ? | ☐ |
| 5 | Y a-t-il de la **pagination** sur toutes les collections ? | ☐ |
| 6 | Les DTOs sont-ils des **Records** + Bean Validation ? | ☐ |
| 7 | Le front peut-il travailler avec un **mock** dès maintenant ? | ☐ |
| 8 | Les breaking changes sont-ils documentés / versionnés ? | ☐ |

**Décision :** ☐ Approuvé  ☐ À retravailler  
**Commentaires :**
