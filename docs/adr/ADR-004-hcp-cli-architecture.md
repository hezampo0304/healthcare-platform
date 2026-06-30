# ADR-004 – HCP CLI Architecture

* **Status:** Accepted
* **Date:** 2026-06-30
* **Decision Makers:** Healthcare Platform Architecture Team
* **Related ADRs:**

  * ADR-001 – Monorepo Strategy
  * ADR-002 – Clean Architecture
  * ADR-003 – Domain-Driven Design (DDD)

---

# Context

The Healthcare Platform is composed of multiple independent backend services, frontend applications, shared libraries, infrastructure modules, and documentation.

Creating and maintaining these projects manually introduces several problems:

* Inconsistent project structures.
* Repetitive setup tasks.
* Diverging coding standards.
* Configuration drift between services.
* Slow onboarding for new developers.
* Increased maintenance costs.

To ensure consistency across the platform, the project requires an internal engineering tool capable of generating projects, enforcing standards, and automating repetitive tasks.

---

# Decision

A dedicated command-line application named **HCP CLI (Healthcare Platform CLI)** will be developed as part of the Platform Engineering toolkit.

The HCP CLI will become the official developer interface for creating and maintaining projects within the Healthcare Platform ecosystem.

The CLI will follow the same architectural principles adopted by backend services:

* Clean Architecture
* Domain-Driven Design (DDD)
* SOLID Principles
* Ports and Adapters (Hexagonal Architecture)

Although it is not a microservice, the CLI will be treated as a production-grade software product.

---

# Goals

The HCP CLI must:

* Standardize project creation.
* Reduce manual configuration.
* Enforce platform standards.
* Improve developer experience.
* Automate repetitive engineering tasks.
* Scale with the platform as new project types are introduced.

---

# Non-Goals

The CLI is not intended to:

* Replace build tools such as Gradle.
* Replace CI/CD pipelines.
* Manage production deployments.
* Replace Infrastructure as Code tools.
* Execute business logic from platform services.

---

# Architectural Principles

The CLI follows these principles:

* Single Responsibility Principle.
* Dependency Inversion Principle.
* Explicit domain model.
* Framework-independent core.
* Testability.
* Extensibility through adapters.
* Consistent developer experience.

Business rules must remain independent of infrastructure concerns.

---

# High-Level Architecture

```text
                    +----------------------+
                    |      Picocli CLI     |
                    +----------+-----------+
                               |
                    Application Layer
                               |
                    Domain Layer
                               |
                 Infrastructure Layer
```

The CLI acts as the presentation layer and delegates all operations to application use cases.

Infrastructure components provide technical implementations such as file generation, template rendering, and console interaction.

---

# Layer Responsibilities

## CLI Layer

Responsible for:

* Parsing commands.
* Parsing arguments.
* Displaying output.
* Handling exit codes.

This layer must not contain business logic.

---

## Application Layer

Responsible for:

* Executing use cases.
* Coordinating workflows.
* Validating requests.
* Invoking infrastructure through ports.

---

## Domain Layer

Contains:

* Domain entities.
* Value objects.
* Domain services.
* Validation rules.
* Domain exceptions.

The domain layer must not depend on external frameworks.

---

## Infrastructure Layer

Provides implementations for:

* File system operations.
* Template engine.
* Console interaction.
* Logging.
* Configuration loading.

Infrastructure depends on the application layer, never the opposite.

---

# Technology Stack

| Component          | Technology          |
| ------------------ | ------------------- |
| Language           | Java 21             |
| Build Tool         | Gradle (Kotlin DSL) |
| CLI Framework      | Picocli             |
| Template Engine    | Pebble              |
| Logging            | SLF4J + Logback     |
| JSON               | Jackson             |
| Testing            | JUnit 5             |
| Mocking            | Mockito             |
| Assertions         | AssertJ             |
| Architecture Tests | ArchUnit            |

---

# Domain Model

The initial domain model consists of the following concepts.

## ServiceDefinition

Represents a backend service to be generated.

Attributes include:

* Service name.
* Description.
* Base package.
* Server port.
* Database name.
* Template type.
* Enabled features.

---

## Feature

Represents optional capabilities that may be included in a generated project.

Examples:

* SECURITY
* POSTGRES
* FLYWAY
* DOCKER
* OPENAPI
* RABBITMQ
* REDIS
* TESTCONTAINERS
* OBSERVABILITY

---

## Template

Represents a project template.

Initial supported templates:

* BACKEND_SERVICE

Future templates:

* ANGULAR_FRONTEND
* WORKER
* BATCH
* LIBRARY

---

# Ports

## Input Ports

* CreateServiceUseCase
* ListTemplatesUseCase
* ValidateDefinitionUseCase

---

## Output Ports

* TemplateEnginePort
* FileSystemPort
* ConsolePort
* LoggerPort

---

# Command Structure

The CLI follows a hierarchical command model.

```text
hcp
├── create
│   ├── service
│   ├── worker
│   ├── batch
│   └── frontend
├── list
│   ├── templates
│   └── services
├── validate
│   ├── architecture
│   └── structure
├── quality
│   └── run
├── doctor
├── version
└── help
```

Every command must delegate its execution to an application use case.

---

# Template Engine

Project generation will be based on templates.

Templates will contain placeholders such as:

* {{SERVICE_NAME}}
* {{BASE_PACKAGE}}
* {{DATABASE_NAME}}
* {{SERVER_PORT}}

The Pebble template engine will be responsible for resolving placeholders.

---

# Error Handling

Errors must be categorized into:

* Validation errors.
* Template errors.
* File system errors.
* Configuration errors.
* Unexpected runtime errors.

The CLI must provide clear and actionable messages for each category.

---

# Logging

The CLI will use structured logging through SLF4J and Logback.

Verbose logging may be enabled through command-line options.

Sensitive information must never be logged.

---

# Testing Strategy

The CLI must include:

* Unit tests for domain and application layers.
* Integration tests for template generation.
* Architecture tests using ArchUnit.
* Command parsing tests.

No feature may be merged without automated tests.

---

# Extensibility

The architecture must allow new commands and templates to be added without modifying existing business logic.

New infrastructure implementations must be added through output ports.

Future versions may support a plugin mechanism while preserving backward compatibility.

---

# Consequences

## Positive

* Consistent project generation.
* Reduced onboarding effort.
* Faster development.
* Centralized engineering standards.
* High maintainability.
* Easier platform evolution.

## Negative

* Initial implementation effort.
* Ongoing maintenance of templates.
* Need for version compatibility between CLI and templates.

---

# Alternatives Considered

## Maven Archetype

Rejected due to limited flexibility and difficulty evolving custom project structures.

## Spring Initializr

Rejected because it focuses only on Spring Boot project generation and does not address platform-specific requirements.

## Cookiecutter

Rejected because it introduces a Python dependency into a Java-centric ecosystem.

## Custom Java CLI

Accepted because it provides complete control over project generation, aligns with the platform's technology stack, and supports future extensibility.

---

# Future Evolution

Planned future capabilities include:

* Plugin architecture.
* Automatic port allocation.
* Project scaffolding for frontend applications.
* Kubernetes manifest generation.
* Documentation generation.
* Architecture validation.
* Dependency upgrades.
* Quality automation.

---

# Summary

The HCP CLI is a strategic component of the Healthcare Platform.

It is not merely a project generator but the official Platform Engineering tool responsible for enforcing architectural standards, improving developer productivity, and ensuring consistency across the entire ecosystem.

All future project generation and engineering automation should be implemented through the HCP CLI.
