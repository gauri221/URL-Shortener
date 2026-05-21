URL Shortener API
A REST API built with Spring Boot that allows users to shorten URLs, resolve them, and track analytics. Secured with JWT authentication and backed by PostgreSQL (Neon).

Tech Stack

Java, Spring Boot
Spring Security, JWT (jjwt)
Spring Data JPA, Hibernate
PostgreSQL (Neon)
Lombok, Maven


Features

User registration and login with JWT authentication
URL shortening with MD5 hashing and collision handling
Short URL resolution with HTTP 302 redirect
Click count tracking on every redirect
Per-user analytics dashboard
Input validation and global exception handling


Project Structure
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
├── url/
│   ├── ShortUrl.java
│   ├── UrlRepository.java
│   ├── UrlService.java
│   ├── UrlController.java
│   └── dto/
│       ├── ShortenRequestDto.java
│       └── ShortenResponseDto.java
├── analytics/
│   ├── AnalyticsController.java
│   └── AnalyticsResponseDto.java
└── exceptions/
    ├── UserAlreadyExistsException.java
    └── GlobalExceptionHandler.java

Getting Started
Prerequisites

Java 17+
Maven
A Neon PostgreSQL account (free at neon.tech)

Environment Variables
Set the following environment variables before running:
VariableDescriptionurlShortner_db_urlNeon PostgreSQL JDBC URLDATABASE_USERNAMEDatabase usernameURLshortner_Database_PasswordDatabase passwordJWT_SECRETSecret key for signing JWT tokens (min 32 characters)
Run The App
bash./mvnw spring-boot:run
The app starts on http://localhost:8080.
Tables are created automatically by Hibernate on first run.

API Endpoints
Auth
Register
POST /auth/signup

Body:
{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
}

Response: 201 Created
{
    "token": "eyJhbG...",
    "email": "john@example.com",
    "name": "John Doe"
}
Login
POST /auth/login

Body:
{
    "email": "john@example.com",
    "password": "password123"
}

Response: 200 OK
{
    "token": "eyJhbG...",
    "email": "john@example.com",
    "name": "John Doe"
}

URL Shortening
Shorten a URL
POST /short
Authorization: Bearer <token>

Body:
{
    "longUrl": "https://www.example.com/some/very/long/url"
}

Response: 201 Created
{
    "shortCode": "aa747c",
    "longUrl": "https://www.example.com/some/very/long/url",
    "createdBy": "John Doe",
    "createdAt": "2026-05-19T18:10:48Z",
    "expiryDate": null
}
Resolve a Short URL
GET /{shortCode}

Response: 302 Found
Redirects browser to the original URL
No authentication required. Anyone with the short link can use it.

Analytics
Get Your URL Analytics
GET /analytics/me
Authorization: Bearer <token>

Response: 200 OK
[
    {
        "shortCode": "aa747c",
        "longUrl": "https://www.example.com/some/very/long/url",
        "clickCount": 5,
        "createdAt": "2026-05-19T18:10:48Z"
    }
]

How URL Shortening Works

User sends a long URL
If the URL was already shortened, the existing short code is returned
Otherwise, the URL is hashed using MD5 and the first 6 characters are taken as the short code
If a collision is detected (short code already exists), a counter is appended and incremented until a unique code is found
The short URL is saved to the database and returned


Security

Passwords are hashed using BCrypt before storage
JWT tokens expire after 30 minutes
All endpoints except /auth/** and GET /{shortCode} require a valid JWT token
Credentials and secrets are managed via environment variables


Future Improvements

Custom short codes chosen by the user
URL expiry date support
AI-powered URL summarization
Rate limiting per user
Browser extension frontend