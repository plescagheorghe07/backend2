# Update - Lectia 4

## Ce s-a adaugat

- `GET /api/auth/me`: controller subtire, logica in `AuthService`, raspuns tipizat `MeResponse`.
- `/me` este protejat (JWT). Register si login raman publice.
- Refactor SOLID pe cache: `CacheController` doar ruteaza, `CacheService` goleste cache-ul.
- Extract Method in `ProductService.list` (`findProducts`).
- `Role.authority()` in loc de string magic `ROLE_`.
- 15 teste pe comportamentul existent + `/me`.

## De ce

Lectia 4 cere clean code si SOLID pe proiect: nu Map in controller, DTO ca restul API-ului, si un al doilea punct refactorizat fara sa schimbam comportamentul.

## Cum verifici

```bash
export JAVA_HOME="$(/usr/libexec/java_home -v 22)"
./mvnw test
./mvnw spring-boot:run
```

1. Login: `POST /api/auth/login`
2. `GET /api/auth/me` cu `Authorization: Bearer <token>`
3. Fara token, `/me` trebuie sa dea 401
4. Swagger: Authorize, apoi testeaza `/api/auth/me`
