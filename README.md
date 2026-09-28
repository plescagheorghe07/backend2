# E-Commerce Backend (Anul 2, lectiile 1-4)

Backend Spring Boot pentru platforma de e-commerce. Acopera REST, PostgreSQL, JWT, Redis, Swagger si SOLID.

## Cerinte

- Java 17+
- Maven 3.9+ (sau Maven Wrapper din proiect)
- PostgreSQL (baza `ecommerce`)
- Redis pe portul 6379

## Pornire

```bash
PGPASSWORD=postgres psql -h localhost -U postgres -c "CREATE DATABASE ecommerce;"
brew services start redis
export JAVA_HOME="$(/usr/libexec/java_home -v 22)"
./mvnw spring-boot:run
```

Daca baza exista deja, comanda `CREATE DATABASE` poate fi sarita.

Aplicatia porneste pe `http://localhost:8080`.

## Rute

- Practica HTTP: `/api/practice` (GET, POST, PUT, PATCH, DELETE)
- Register: `POST /api/auth/register`
- Login: `POST /api/auth/login`
- Utilizator curent: `GET /api/auth/me` (JWT)
- Produse: `GET /api/products`
- Categorii: `GET /api/categories`
- Golire cache: `DELETE /api/cache`
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Dupa login, in Swagger apasa **Authorize** si lipeste tokenul JWT.

## Structura

```
controller  ->  service  ->  repository  ->  PostgreSQL
                     |
                   Redis cache (produse, categorii, TTL 60s)
```

Entitatile JPA nu se trimit niciodata direct clientului. API-ul expune doar DTO-uri.
