# ADR-005 – Dependency Injection and Composition Root

- **Status:** Accepted
- **Date:** 2026-06-30
- **Decision Makers:** Healthcare Platform Team

---

# Context

Healthcare Platform is composed of multiple applications, including backend microservices, engineering tools, libraries, and frontend applications.

All these components require dependency management while preserving the principles of Clean Architecture, Domain-Driven Design (DDD), and low coupling.

One of the architectural decisions is defining where object creation should occur and how dependencies should be assembled.

Without a clear strategy, object creation can become scattered across the application, increasing coupling and making testing more difficult.

---

# Decision

Healthcare Platform adopts the **Composition Root** pattern as the standard mechanism for assembling object graphs.

Business logic **must never instantiate its own dependencies**.

Object creation will always occur at the application's entry point.

Examples include:

- CLI applications
- Spring Boot applications
- AWS Lambda handlers
- Scheduled jobs
- Batch processes

Each executable application is responsible for assembling its own dependency graph.

---

# Dependency Injection Strategy

Healthcare Platform defines three evolution stages.

## Stage 1 — Manual Dependency Injection

Small applications shall use manual dependency injection.

Example:

```java
CommandExecutorPort executor = new ProcessCommandExecutor();

ToolChecker javaChecker = new JavaChecker(executor);

RunDoctorUseCase useCase =
        new RunDoctorUseCase(List.of(javaChecker));

DoctorCommand command =
        new DoctorCommand(useCase);
```

This approach provides:

- Explicit dependencies
- Fast startup
- No external framework
- Easy debugging

This is the default strategy for CLI applications.

---

## Stage 2 — Composition Modules

As the application grows, dependency creation should be organized into dedicated composition modules.

Example:

```
configuration/

ApplicationModule

DoctorModule

GeneratorModule

TemplateModule
```

Each module becomes responsible for assembling a bounded context of the application.

This keeps the Composition Root organized without introducing a dependency injection framework.

---

## Stage 3 — Dependency Injection Framework

Large applications may adopt a dependency injection framework when justified.

Examples include:

- Spring Dependency Injection
- Google Guice
- Dagger

The choice of framework depends on the application type.

For example:

| Application | Recommendation |
|-------------|----------------|
| Spring Boot Microservices | Spring DI |
| CLI | Manual DI |
| AWS Lambda | Manual DI or Dagger |
| Libraries | Manual DI |

Dependency Injection frameworks are considered implementation details and must never leak into the Domain or Application layers.

---

# Composition Root

Each executable application shall contain a single Composition Root.

Responsibilities include:

- Creating infrastructure adapters
- Creating repositories
- Creating external clients
- Creating use cases
- Wiring dependencies
- Starting the application

Business logic must never instantiate infrastructure objects.

---

# Architectural Rules

The following rules are mandatory.

## Rule 1

Domain must never instantiate infrastructure classes.

✔ Allowed

```
UseCase
    ↓
RepositoryPort
```

✘ Forbidden

```
UseCase
    ↓
new MongoRepository()
```

---

## Rule 2

Use cases receive dependencies via constructor injection.

✔

```java
public RunDoctorUseCase(
        List<ToolChecker> checkers) {
}
```

✘

```java
public class RunDoctorUseCase {

    private ProcessCommandExecutor executor =
            new ProcessCommandExecutor();

}
```

---

## Rule 3

Infrastructure depends on Application.

Application never depends on Infrastructure.

---

## Rule 4

Dependency Injection frameworks are infrastructure concerns.

They must never appear inside:

- Domain
- Application

---

# Consequences

## Positive

- Low coupling
- Better testability
- Explicit dependencies
- Easier maintenance
- Framework independence
- Improved readability
- Faster startup for CLI applications

## Negative

- Manual wiring requires additional code.
- Composition Root grows as the application grows.
- Refactoring may be required when introducing modules.

These trade-offs are considered acceptable.

---

# Alternatives Considered

## Spring Dependency Injection Everywhere

Rejected.

Reason:

- Introduces unnecessary complexity into CLI applications.
- Increases startup time.
- Couples applications to Spring.

---

## Service Locator Pattern

Rejected.

Reason:

- Hidden dependencies.
- Difficult testing.
- Violates Clean Architecture principles.

---

## Static Factories

Rejected.

Reason:

- Difficult to mock.
- Harder to extend.
- Encourages global state.

---

# Compliance

Future architecture tests (ArchUnit) should verify:

- Domain does not instantiate infrastructure.
- Application does not depend on infrastructure packages.
- Constructor injection is preferred.
- Composition Root exists only at the application boundary.

---

# References

- Clean Architecture — Robert C. Martin
- Dependency Injection Principles, Practices and Patterns
- Domain-Driven Design — Eric Evans
- Implementing Domain-Driven Design — Vaughn Vernon
- Architecture Patterns with Python