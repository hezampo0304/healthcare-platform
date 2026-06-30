# Coding Guidelines

**Version:** 1.0

**Status:** Active

**Last Updated:** 2026-06-30

**Related ADRs**

* ADR-001 – Monorepo Strategy
* ADR-002 – Clean Architecture
* ADR-003 – Domain-Driven Design (DDD)

---

# 1. Purpose

This document defines the mandatory coding standards for every component developed within the Healthcare Platform.

The objective is to ensure:

* Consistency across all microservices.
* Readable and maintainable code.
* High testability.
* Low coupling.
* High cohesion.
* Long-term maintainability.

These guidelines apply to every backend service developed in this repository.

---

# 2. RFC 2119 Keywords

The following keywords are interpreted according to RFC 2119.

| Keyword    | Meaning               |
| ---------- | --------------------- |
| MUST       | Mandatory             |
| MUST NOT   | Prohibited            |
| SHOULD     | Strong recommendation |
| SHOULD NOT | Discouraged           |
| MAY        | Optional              |

---

# 3. Technology Standards

| Component        | Standard                           |
| ---------------- | ---------------------------------- |
| Java             | 21 LTS                             |
| Spring Boot      | 3.x                                |
| Build Tool       | Gradle                             |
| Database         | PostgreSQL                         |
| Migration        | Flyway                             |
| Testing          | JUnit 5 + Mockito + Testcontainers |
| Documentation    | OpenAPI                            |
| Logging          | SLF4J                              |
| Containerization | Docker                             |

---

# 4. Architectural Principles

Every microservice MUST follow:

* Clean Architecture
* Domain-Driven Design (DDD)
* Hexagonal Architecture
* SOLID Principles

Business rules MUST remain independent from frameworks.

---

# 5. Dependency Rule

Dependencies MUST always point toward the domain.

Allowed:

```text
REST Controller
      ↓
Use Case
      ↓
Domain
```

Forbidden:

```text
Domain
      ↓
Spring
```

```text
Domain
      ↓
JPA
```

```text
Domain
      ↓
Controller
```

---

# 6. Controllers

Controllers MUST:

* Receive HTTP requests.
* Validate input.
* Call exactly one use case.
* Return HTTP responses.

Controllers MUST NOT:

* Implement business logic.
* Access repositories.
* Call EntityManager.
* Perform database queries.

Correct:

```java
@PostMapping("/login")
public LoginResponse login(@RequestBody LoginRequest request) {
    return authenticateUserUseCase.execute(request);
}
```

Incorrect:

```java
@PostMapping("/login")
public LoginResponse login(...) {
    User user = repository.findByEmail(...);
    ...
}
```

---

# 7. Use Cases

Each use case MUST:

* Represent one business action.
* Have one responsibility.
* Be framework-independent.
* Use ports instead of infrastructure implementations.

Examples:

* AuthenticateUserUseCase
* CreateAppointmentUseCase
* CancelAppointmentUseCase

Avoid:

* UserService
* AppointmentManager

---

# 8. Domain Model

The domain is the core of the application.

The domain MUST NOT depend on:

* Spring
* Hibernate
* JPA
* Jackson
* Lombok (unless explicitly approved)
* REST
* Messaging frameworks

Business rules belong inside the domain.

---

# 9. Rich Domain Model

Entities MUST encapsulate behavior.

Correct:

```java
user.lock();
user.changePassword(password);
appointment.cancel(reason);
```

Avoid:

```java
user.setLocked(true);
user.setPassword(password);
appointment.setStatus(CANCELLED);
```

---

# 10. Value Objects

Primitive obsession MUST be avoided.

Incorrect:

```java
String email;
String phone;
String password;
```

Correct:

```java
Email email;
PhoneNumber phone;
Password password;
```

Value Objects MUST:

* Be immutable.
* Validate themselves.
* Override equals() and hashCode().

---

# 11. Entities

Entities MUST:

* Have identity.
* Encapsulate behavior.
* Protect invariants.

Entities SHOULD NOT expose public setters.

---

# 12. Repositories

Repositories MUST be interfaces.

Example:

```java
public interface UserRepository {
    Optional<User> findByEmail(Email email);
}
```

Implementations belong in Infrastructure.

---

# 13. Dependency Injection

Constructor injection MUST be used.

Correct:

```java
public AuthenticateUserUseCase(UserRepository repository) {
    this.repository = repository;
}
```

Avoid:

```java
@Autowired
private UserRepository repository;
```

---

# 14. Exceptions

Generic exceptions MUST NOT be thrown.

Avoid:

```java
throw new Exception();
```

Use domain-specific exceptions.

Examples:

* InvalidCredentialsException
* UserLockedException
* AppointmentNotFoundException

---

# 15. Null Handling

Avoid returning null.

Preferred:

```java
Optional<User>
```

Collections MUST return empty collections instead of null.

---

# 16. Logging

Use SLF4J.

Never use:

```java
System.out.println();
```

Sensitive information MUST NEVER be logged.

Examples:

* Passwords
* JWTs
* Credit card numbers
* Medical records

---

# 17. Constants

Magic numbers MUST NOT be used.

Correct:

```java
private static final int MAX_LOGIN_ATTEMPTS = 5;
```

---

# 18. Methods

Methods SHOULD:

* Perform one responsibility.
* Have descriptive names.
* Avoid excessive complexity.

Recommended:

* Maximum 20–30 lines.
* Maximum 3 levels of nesting.

---

# 19. Classes

Classes SHOULD:

* Follow the Single Responsibility Principle.
* Avoid excessive size.

Recommended:

* Less than 300 lines.

---

# 20. Naming Conventions

Classes

* User
* Appointment
* Pharmacy

Interfaces

* UserRepository
* TokenProvider

Use Cases

* AuthenticateUserUseCase
* CreateAppointmentUseCase

DTOs

* LoginRequest
* LoginResponse

Controllers

* AuthController
* AppointmentController

Configuration

* SecurityConfiguration
* OpenApiConfiguration

---

# 21. Package Naming

Packages MUST use lowercase.

Example:

```text
com.healthcare.platform.auth.domain
```

---

# 22. Immutability

Prefer immutable objects.

Fields SHOULD be final whenever possible.

---

# 23. Mapping

Entities MUST NEVER be exposed through REST APIs.

Use DTOs.

Entity

↓

Mapper

↓

DTO

---

# 24. Validation

Validation responsibilities:

Controller

* Input format.

Domain

* Business rules.

Database

* Persistence constraints.

---

# 25. Testing

Every feature MUST include tests.

Recommended minimum coverage:

* Line Coverage ≥ 90%
* Mutation Score ≥ 80%

Testing pyramid:

* Unit Tests
* Integration Tests
* End-to-End Tests

Business rules SHOULD be tested without starting Spring Boot.

---

# 26. Code Formatting

The project MUST use automated formatting.

Rules:

* 4 spaces indentation.
* UTF-8 encoding.
* Unix line endings (LF).
* Remove trailing whitespace.

Formatting MUST be enforced automatically.

---

# 27. Code Reviews

Every Pull Request MUST verify:

* Architecture compliance.
* Readability.
* Test coverage.
* Naming conventions.
* Documentation updates.
* No duplicated code.

---

# 28. Security

Developers MUST:

* Validate all external input.
* Never hardcode secrets.
* Use parameterized queries.
* Store passwords using strong hashing algorithms.
* Keep dependencies up to date.

---

# 29. Documentation

Public classes SHOULD include Javadoc when their purpose is not obvious.

Complex business rules SHOULD explain **why** they exist, not **what** the code does.

---

# 30. Definition of Done

Code is considered complete only if:

* Architecture rules are respected.
* All tests pass.
* Coverage requirements are met.
* No critical static analysis issues exist.
* OpenAPI documentation is updated.
* Flyway migrations are included (if applicable).
* Documentation is updated.
* Code review has been completed.

---

# Guiding Principle

> "Code should be written for humans first and computers second."

Healthcare Platform prioritizes maintainability, readability, and business clarity over clever or overly complex implementations.

Every line of code should contribute to a system that is easier to understand, test, and evolve.
