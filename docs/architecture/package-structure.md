# Package Structure

**Version:** 1.0

**Status:** Active

**Last Updated:** 2026-06-30

**Related ADRs**

* ADR-001 – Monorepo Strategy
* ADR-002 – Clean Architecture
* ADR-003 – Domain-Driven Design (DDD)

---

# 1. Purpose

This document defines the standard package and project structure for every backend microservice in the Healthcare Platform.

A consistent structure improves:

* Maintainability
* Readability
* Team productivity
* Code navigation
* Onboarding
* Scalability

Every microservice MUST follow this structure.

---

# 2. Project Structure

Each microservice MUST be an independent Gradle project.

Example:

```text
auth-service/
│
├── src/
├── build.gradle.kts
├── settings.gradle.kts
├── Dockerfile
├── README.md
└── .gitignore
```

---

# 3. Source Structure

```text
src
├── main
│   ├── java
│   └── resources
│
└── test
    ├── java
    └── resources
```

---

# 4. Base Package

Every service MUST use:

```text
com.healthcare.platform
```

Example:

```text
com.healthcare.platform.auth
```

```text
com.healthcare.platform.doctor
```

```text
com.healthcare.platform.appointment
```

---

# 5. Standard Package Layout

Every microservice MUST follow this structure.

```text
com.healthcare.platform.auth

├── application
│
├── domain
│
├── infrastructure
│
├── entrypoint
│
└── shared
```

Each package has a single responsibility.

---

# 6. Application Layer

Contains application-specific business orchestration.

```text
application
│
├── usecase
│
├── ports
│   ├── input
│   └── output
│
├── mapper
│
└── service
```

### Responsibilities

* Execute use cases.
* Coordinate business operations.
* Depend only on the domain.
* Communicate through ports.

The application layer MUST NOT contain infrastructure code.

---

# 7. Domain Layer

The most important layer.

```text
domain
│
├── model
│
├── valueobject
│
├── event
│
├── service
│
├── exception
│
├── specification
│
├── repository
│
└── validation
```

The domain MUST NOT depend on Spring or any framework.

---

## 7.1 model

Contains entities and aggregate roots.

Example:

```text
User

Doctor

Appointment

Prescription
```

---

## 7.2 valueobject

Contains immutable value objects.

Examples:

```text
Email

Password

UserId

AppointmentId

MedicineCode

Money

PhoneNumber
```

---

## 7.3 event

Contains domain events.

Examples:

```text
UserAuthenticated

AppointmentCreated

MedicineDispensed
```

---

## 7.4 service

Contains Domain Services.

Only business logic that does not belong to an entity.

---

## 7.5 repository

Repository interfaces.

Example:

```text
UserRepository

AppointmentRepository
```

No implementation belongs here.

---

## 7.6 exception

Contains domain-specific exceptions.

Examples:

```text
UserLockedException

AppointmentNotAvailableException

InvalidCredentialsException
```

---

# 8. Infrastructure Layer

Contains technical implementations.

```text
infrastructure
│
├── persistence
│
├── security
│
├── messaging
│
├── configuration
│
├── client
│
├── scheduler
│
├── cache
│
└── mapper
```

---

## 8.1 persistence

Database implementations.

```text
entity

repository

adapter
```

Contains:

* JPA entities
* Spring repositories
* Repository adapters

---

## 8.2 security

Contains:

* JWT
* Spring Security
* Authentication filters
* Token generation

---

## 8.3 messaging

Contains:

* RabbitMQ
* Kafka
* Event publishers
* Event consumers

---

## 8.4 configuration

Spring configuration classes.

Examples:

```text
SecurityConfiguration

OpenApiConfiguration

DatabaseConfiguration

RabbitConfiguration
```

---

## 8.5 client

REST clients.

Feign clients.

WebClient.

External APIs.

---

## 8.6 scheduler

Scheduled jobs.

Cron tasks.

Background processing.

---

## 8.7 cache

Redis adapters.

Local cache.

Distributed cache.

---

# 9. Entrypoint Layer

Represents all inbound adapters.

```text
entrypoint
│
├── rest
│
├── dto
│
├── mapper
│
├── advice
│
└── validation
```

---

## rest

REST Controllers.

Only HTTP.

---

## dto

Request and Response objects.

Never expose domain entities.

---

## mapper

Entity ↔ DTO mapping.

---

## advice

Global exception handlers.

Problem Details (RFC 7807).

---

## validation

Bean Validation.

Custom validators.

---

# 10. Shared Package

Contains reusable components that belong only to the microservice.

```text
shared
│
├── constants
├── util
├── configuration
└── exception
```

Avoid creating a "God package."

---

# 11. Test Structure

The test package MUST mirror production code.

```text
src/test/java

application

domain

infrastructure

entrypoint
```

Tests should be easy to locate.

---

# 12. Resources

```text
resources

application.yml

db

messages

openapi

logback.xml
```

---

## Flyway

```text
db

migration

V1__initial_schema.sql

V2__create_user.sql
```

---

## OpenAPI

```text
openapi

auth-api.yaml
```

---

# 13. Package Responsibilities

| Package        | Responsibility            |
| -------------- | ------------------------- |
| domain         | Business rules            |
| application    | Use cases                 |
| infrastructure | Technical implementations |
| entrypoint     | External interfaces       |
| shared         | Internal shared utilities |

---

# 14. Dependency Rules

Allowed:

```text
Entrypoint
      ↓
Application
      ↓
Domain
```

Infrastructure implements interfaces defined by the application or domain.

Forbidden:

```text
Domain
      ↓
Infrastructure
```

```text
Application
      ↓
Controller
```

```text
Controller
      ↓
Repository
```

---

# 15. Naming Conventions

Controllers

```text
AuthController

DoctorController
```

Use Cases

```text
AuthenticateUserUseCase

CreateAppointmentUseCase
```

Repositories

```text
UserRepository
```

Adapters

```text
PostgresUserRepositoryAdapter
```

DTOs

```text
LoginRequest

LoginResponse
```

Events

```text
AppointmentCreatedEvent
```

Configurations

```text
SecurityConfiguration
```

---

# 16. Forbidden Practices

The following are prohibited:

* Business logic inside controllers.
* JPA entities exposed through APIs.
* Static utility classes for business logic.
* Cross-layer dependencies that violate Clean Architecture.
* Shared databases between microservices.
* Circular dependencies.

---

# 17. Recommended Evolution

Every new microservice MUST start by copying this structure before implementing business logic.

Changes to the standard package layout require an Architecture Decision Record (ADR) and approval through code review.

---

# Summary

All Healthcare Platform microservices share a common structure to ensure consistency, maintainability, and scalability.

A developer familiar with one service should be able to navigate any other service with minimal effort.

The package structure defined in this document is mandatory and serves as the foundation for every backend component in the platform.
