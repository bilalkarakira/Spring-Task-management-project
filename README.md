# TaskManager

Lightweight Spring Boot task management REST API with JWT-based authentication.

## Overview

This project is a simple Task Manager API implemented with Spring Boot. It provides endpoints to sign up / log in users (JWT tokens) and CRUD endpoints for tasks. Data is persisted using an embedded H2 file database located in the repository `data/` folder so the project can be run locally without extra DB setup.

The README below explains how to set up, run, and test the application locally and gives a quick guide for reviewers who want to inspect or run the code.

## Tech / Tools

- Java 17
- Spring Boot (Web, Data JPA, Security)
- H2 (file-based, runtime)
-- Maven (`mvn`)
- jjwt (JSON Web Token library)
- MapStruct, Lombok (compile-time conveniences)

## Prerequisites

- JDK 17 installed and JAVA_HOME configured
- Git (if cloning)
-- Ensure Maven is installed and available as `mvn` on your PATH.

## Quick start (Windows)

1. Open a terminal (cmd.exe) and change to the project root directory (where this README and `pom.xml` live):

```cmd
cd {project_directory}
```

2. Build the project and run tests:

```cmd
mvn clean package
```

3. Run the application (development run using the wrapper):

```cmd
mvn spring-boot:run
```

Or run the built jar:

```cmd
java -jar target\taskmanager-0.0.1-SNAPSHOT.jar
```

The application will start on port 8080 by default.

## Configuration (what's already set)

- Application name: `taskmanager`
- Datasource (H2 file-based): `jdbc:h2:file:./data/taskdb;AUTO_SERVER=TRUE` (see `src/main/resources/application.yaml`)
- H2 console is enabled at: `http://localhost:8080/h2-console` (JDBC URL above, username `sa`, password `password`)
- JWT secret is set in `application.yaml` under `app.security.secret`. Tokens currently expire in 15 minutes by default.

Note: A file `data/taskdb.mv.db` is present in the repository (committed) so running the app will reuse that database file. If you want a fresh DB, either delete the `data/taskdb*` files or use an in-memory URL for testing.

## Authentication

- Public endpoints: `/auth/signup` and `/auth/login` (configured in `SecurityConfig` to be permitted)
- Other endpoints require Authorization header with a Bearer token: `Authorization: Bearer <token>`
- Tokens are JWTs signed with the secret from `application.yaml` and have 15 minutes expiration (see `JWTService`).

Example requests (using curl):

Signup (creates user and returns token):

```cmd
curl -X POST http://localhost:8080/auth/signup -H "Content-Type: application/json" -d "{\"email\":\"alice@example.com\",\"password\":\"password123\"}"
```

Login (returns token):

```cmd
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d "{\"email\":\"alice@example.com\",\"password\":\"password123\"}"
```

Use the returned token for protected endpoints, for example listing tasks:

```cmd
curl -H "Authorization: Bearer <token>" http://localhost:8080/tasks
```

Example create task (JSON body uses fields from `dto.TaskRequest` — see DTO package):

```cmd
curl -X POST http://localhost:8080/tasks -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d "{\"title\":\"My task\", \"description\":\"Do the thing\"}"
```

## API Endpoints (summary)

- POST /auth/signup — register user, returns { "token": "..." }
- POST /auth/login — login, returns { "token": "..." }
- GET /tasks — list tasks (requires auth)
- GET /tasks/{id} — get single task (requires auth)
- POST /tasks — create task (requires auth)
- PUT /tasks/{id} — update task (requires auth)
- DELETE /tasks/{id} — delete task (requires auth)

Also available:
- GET /hello — simple health/test endpoint that returns `Pong`
- H2 console: GET /h2-console (UI)

## Project structure (high level)

- com.bilal.taskmanager — main application package
  - config — security configuration, JWT filter, etc. (e.g. `SecurityConfig`, `JwtAuthFilter`)
  - controller — REST controllers (e.g. `AuthController`, `TaskController`)
  - dto — request/response DTOs (e.g. `TaskRequest`, `AuthRequest`)
  - model — JPA entities (e.g. `Task`, `User`)
  - repository — Spring Data JPA repositories
  - service — business logic and JWT utilities (e.g. `AuthService`, `JWTService`, `CustomUserDetailsService`)
  - util — helpers/parsers
  - exception — custom exceptions and handlers

Key files to inspect during review:
- `src/main/java/com/bilal/taskmanager/config/SecurityConfig.java` — security setup, JWT filter registration
- `src/main/java/com/bilal/taskmanager/config/JwtAuthFilter.java` — request filter that extracts JWT and authenticates
- `src/main/java/com/bilal/taskmanager/service/JWTService.java` — token generation and validation
- `src/main/java/com/bilal/taskmanager/controller/AuthController.java` — signup/login endpoints
- `src/main/java/com/bilal/taskmanager/controller/TaskController.java` — task CRUD endpoints

## Reviewer checklist

1. Clone the repo and build with `mvn clean package`.
2. Run the app and try the `/auth/signup` and `/auth/login` endpoints. Verify a token is returned.
3. Use the token to call `/tasks` endpoints. Verify authorization is enforced for protected routes.
4. Inspect `application.yaml` for the H2 JDBC URL and JWT secret. Verify token expiration (15 minutes) and that the secret is present in the file (insecure for production — move to env vars in real deployments).
5. Use `http://localhost:8080/h2-console` to inspect the database; JDBC URL: `jdbc:h2:file:./data/taskdb`, user `sa`, password `password`.
6. Run unit/integration tests with `mvn test` and check for failures.

## Troubleshooting

- If you see port conflicts, ensure nothing else listens on 8080 or change server.port in `application.yaml`.
- If the app cannot find the H2 DB file, check file permissions on `data/` and ensure the working directory is the project root when starting the app.
- If JWT validation fails, ensure the request `Authorization` header is `Bearer <token>` and that the token was issued by this app (matching secret).

## Contributing

1. Create a branch for your change.
2. Run tests and ensure they pass.
3. Open a pull request with a clear description of your changes.

## Notes / Caveats

- The JWT secret is stored in `application.yaml` for convenience; this is not secure for production. For production deployments, load the secret from an environment variable or a secrets manager.
- Token lifetime is short (15 minutes). Consider adding refresh tokens if required.
- The current H2 DB is file-based and committed in `data/` for quick reviewer setup. To use a clean DB, remove the `data/taskdb.mv.db` files before first run.

---

If you want, I can also add example Postman collection or Swagger/OpenAPI documentation to the repo. Would you like that?
