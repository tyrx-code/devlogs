# 📓 DevLogs API

> A REST API for tracking your developer journey — log what you're learning, how you're feeling, and how hard things are.

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 1.9.25 |
| Framework | Spring Boot 3.5.10 |
| Persistence | Spring Data JPA |
| Database | PostgreSQL (H2 for local/testing) |
| Auth | Spring Security + JWT (JJWT) |
| Docs | SpringDoc OpenAPI (Swagger UI) |
| Build | Gradle (Kotlin DSL) |
| Java | 17 |

---

## 🚀 Getting Started

### Prerequisites

- JDK 17+
- PostgreSQL (or use H2 for quick local dev)

### Run locally

```bash
git clone https://github.com/your-username/devlogs.git
cd devlogs
./gradlew clean build
./gradlew bootRun
```

The app starts on `http://localhost:8080`.

### Swagger UI

Once running, visit:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔐 Authentication

DevLogs uses **JWT Bearer token** authentication.

### Register

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "tomas",
  "email": "tomas@example.com",
  "password": "yourpassword"
}
```

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "tomas",
  "password": "yourpassword"
}
```

Both return an `AuthResponse`:

```json
{
  "token": "eyJhbGci...",
  "username": "tomas"
}
```

Use the token in subsequent requests:

```http
Authorization: Bearer <your_token>
```

---

## 📋 API Endpoints

All `/api/entries` routes require a valid Bearer token.

### Entries

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/entries` | Get all log entries |
| `POST` | `/api/entries` | Create a new log entry |
| `GET` | `/api/entries/{id}` | Get a specific entry by ID |
| `PUT` | `/api/entries/{id}` | Fully update an entry |
| `PATCH` | `/api/entries/{id}` | Partially update an entry |
| `DELETE` | `/api/entries/{id}` | Delete an entry |

### Create Entry — Request Body

```json
{
  "title": "My first log",
  "content": "Today I learned about coroutines in Kotlin...",
  "difficulty": "MEDIUM",
  "mood": "MOTIVATED",
  "visibility": "PUBLIC",
  "tags": ["kotlin", "coroutines"]
}
```

| Field | Type | Values |
|---|---|---|
| `difficulty` | enum | `EASY`, `MEDIUM`, `HARD` |
| `mood` | enum | `MOTIVATED`, `NEUTRAL`, `TIRED` |
| `visibility` | enum | `PRIVATE`, `PUBLIC` |
| `tags` | string[] | Any unique string labels |

### Entry Response

```json
{
  "id": "uuid",
  "title": "My first log",
  "content": "Today I learned about coroutines...",
  "difficulty": "MEDIUM",
  "mood": "MOTIVATED",
  "visibility": "PUBLIC",
  "tags": ["kotlin", "coroutines"],
  "createdAt": "2025-01-01T10:00:00Z",
  "updatedAt": "2025-01-01T10:00:00Z"
}
```

---

## 🗄️ Database

By default, the app is configured to use **H2 (in-memory)** for easy local development and **PostgreSQL** for production.

To switch to PostgreSQL, update your `application.properties` or `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/devlogs
    username: your_user
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
```

---

## 📁 Project Structure

```
src/
└── main/
    └── kotlin/com/tomas/devlogs/
        ├── auth/          # JWT auth, login/register
        ├── entries/       # Entry entity, controller, service, repository
        └── DevlogsApplication.kt
```

---