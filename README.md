# URL Shortener API

A REST API built with Spring Boot that allows users to shorten URLs, resolve them, and track analytics. Secured with JWT authentication and backed by PostgreSQL (Neon).

---

# Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT (jjwt)
- Spring Data JPA
- Hibernate
- PostgreSQL (Neon)
- Lombok
- Maven

---

# Features

- User registration and login with JWT authentication
- URL shortening with MD5 hashing and collision handling
- Short URL resolution with HTTP 302 redirect
- Click count tracking on every redirect
- Per-user analytics dashboard
- Input validation and global exception handling

---

# Project Structure

```bash
src/main/java/com/example/URLshortener/
├── auth/
│   ├── User.java
│   ├── UserRepository.java
│   ├── AuthService.java
│   ├── AuthController.java
│   ├── JwtUtil.java
│   ├── JwtFilter.java
│   ├── CustomUserDetailsService.java
│   ├── SecurityConfig.java
│   └── dto/
│       ├── RegisterRequest.java
│       ├── LoginRequest.java
│       └── JwtResponse.java
│
├── url/
│   ├── ShortUrl.java
│   ├── UrlRepository.java
│   ├── UrlService.java
│   ├── UrlController.java
│   └── dto/
│       ├── ShortenRequestDto.java
│       └── ShortenResponseDto.java
│
├── analytics/
│   ├── AnalyticsController.java
│   └── AnalyticsResponseDto.java
│
└── exceptions/
    ├── UserAlreadyExistsException.java
    └── GlobalExceptionHandler.java
```

---

# Getting Started

## Prerequisites

Make sure you have the following installed:

- Java 17+ (used Java 21 here)
- Maven
- A Neon PostgreSQL account

---

# Maven Dependencies

I had to add the JWT dependencies manually because some `jjwt` libraries are not included by default when creating a Spring Boot project with Spring Initializr.

```xml
<dependencies>

    <!-- JWT API -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>

    <!-- JWT Implementation -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

    <!-- JWT Jackson Serializer -->
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>

</dependencies>
```

These dependencies are used for:

- Generating JWT tokens
- Validating JWT tokens
- Parsing JWT claims
- JSON serialization/deserialization for JWT payloads

---

# Environment Variables

You need to set the following environment variables before running the application:

| Variable | Description |
|---|---|
| `urlShortner_db_url` | Neon PostgreSQL JDBC URL |
| `DATABASE_USERNAME` | Database username |
| `URLshortner_Database_Password` | Database password |
| `JWT_SECRET` | Secret key for signing JWT tokens (minimum 32 characters) |


tip - restart your ide after setting the environment variables

---

# Run The App

```bash
mvnw spring-boot:run
```

The application starts on:

```bash
http://localhost:8080
```

Hibernate automatically creates the required database tables on first run.

---

# How URL Shortening Works

1. User sends a long URL
2. If the URL was already shortened, the existing short code is returned
3. Otherwise:
   - The URL is hashed using MD5
   - The first 6 characters are used as the short code
4. If a collision occurs:
   - A counter is appended
   - The counter increments until a unique code is generated
5. The short URL is saved to the database
6. The API returns the generated short code

---

# Security

- Passwords are hashed using BCrypt before storage
- JWT tokens expire after 30 minutes
- All endpoints except:
  - `/auth/**`
  - `GET /{shortCode}`
  
  require valid JWT authentication
- Credentials and secrets are managed using environment variables

---

# Future Improvements

- Custom short codes
- URL expiry date support
- AI-powered URL summarisation
- Rate limiting per user
- Browser extension frontend

---

# Author
Gauri Mishra
