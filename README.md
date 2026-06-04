# 🔐 AuthApp — Spring Boot JWT Authentication API

A production-ready **REST API** for user authentication and authorization built with **Spring Boot** and **JWT (JSON Web Tokens)**. Implements industry-standard security practices including stateless authentication, role-based access control, and secure password handling.

---

## ✨ Features

- **JWT-based stateless authentication** — no server-side session storage
- **User registration & login** with input validation via DTOs
- **Spring Security** integration with custom filter chain
- **BCrypt password hashing** for secure credential storage
- **JPA/Hibernate** ORM with MySQL persistence
- Clean layered architecture: Controller → Service → Repository

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.x |
| Security | Spring Security + JWT |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL |
| Build Tool | Maven |
| Language | Java 17+ |

---

## 📁 Project Structure

```
src/main/java/com/example/AuthApp/
├── controller/
│   └── UserController.java       # REST endpoints for auth
├── dto/
│   ├── LoginRequest.java         # Login payload
│   └── RegisterRequest.java      # Registration payload
├── model/
│   └── User.java                 # User entity
├── repository/
│   └── UserRepository.java       # JPA repository
├── security/
│   ├── JwtFilter.java            # JWT request filter
│   ├── JwtUtil.java              # Token generation & validation
│   └── SecurityConfig.java       # Spring Security configuration
└── service/
    └── (UserService)             # Business logic layer
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- MySQL 8.x running locally
- Maven 3.x

### 1. Clone the repository

```bash
git clone https://github.com/Ayan-Maity3105/AuthApp.git
cd AuthApp
```

### 2. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE authapp_db;
```

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/authapp_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update

app.jwt.secret=YOUR_JWT_SECRET_KEY
app.jwt.expiration=86400000
```

> ⚠️ Never commit real credentials. Use environment variables in production.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`

---

## 📬 API Endpoints

### Register a new user

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Response:**
```json
{
  "message": "User registered successfully"
}
```

---

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securepassword"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### Access a protected route

```http
GET /api/user/profile
Authorization: Bearer <your_jwt_token>
```

---

## 🔒 Security Architecture

```
Client Request
     │
     ▼
 JwtFilter          ← Intercepts every request
     │
     ├── No token?  → Return 401 Unauthorized
     │
     ├── Invalid?   → Return 403 Forbidden
     │
     └── Valid?     → Set Authentication in SecurityContext
                            │
                            ▼
                     Controller Layer
```

- **JwtFilter** extends `OncePerRequestFilter` to validate tokens on each request
- **JwtUtil** handles token generation, parsing, and expiry validation using HMAC-SHA256
- **SecurityConfig** defines public routes (register/login) and secures all others

---

## 🗃️ Database Schema

```sql
CREATE TABLE users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL
);
```

---

## 🧪 Testing with Postman

1. Hit `POST /api/auth/register` to create a user
2. Hit `POST /api/auth/login` to receive a JWT token
3. Copy the token and set `Authorization: Bearer <token>` header on protected routes

---

## 📌 Environment Variables (Recommended for Production)

| Variable | Description |
|---|---|
| `DB_URL` | JDBC connection string |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for signing tokens |
| `JWT_EXPIRY` | Token expiry in milliseconds |

---

## 🤝 Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you'd like to change.

---

## 👨‍💻 Author

**Ayan Maity**
- GitHub: [@Ayan-Maity3105](https://github.com/Ayan-Maity3105)
- Email: maityayan473@gmail.com

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
