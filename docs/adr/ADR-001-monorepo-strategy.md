# ADR-001: Monorepo Strategy

**Status**

Accepted

**Date**

2026-06-30

---

# Context

Healthcare Platform is an enterprise-grade healthcare management system composed of multiple independent microservices.

The platform will include services such as:

* Authentication
* Identity Management
* Doctor Management
* Appointment Scheduling
* Medical Records
* Pharmacy
* Inventory
* Billing
* Notifications
* Reporting
* Audit

From the beginning of the project, a repository strategy must be selected to organize the source code and support future development.

The two alternatives considered were:

* Monorepo
* Multi-repository

---

# Decision

The project will adopt a **Monorepo** strategy.

All services, frontend applications, infrastructure, documentation and shared assets will live in a single Git repository named:

`healthcare-platform`

Each microservice will remain an independent Gradle project with its own lifecycle, dependencies and Docker image.

The repository will be organized as follows:

```text
healthcare-platform/
│
├── services/
├── frontend/
├── infrastructure/
├── shared/
├── docs/
└── .github/
```

No service will depend directly on another service's internal implementation.

Communication between services will occur exclusively through:

* REST APIs
* Asynchronous Events
* Shared Contracts (when required)

Each service owns its own database.

Database sharing between services is strictly prohibited.

---

# Rationale

A monorepo provides several advantages during the development of this platform.

### Consistent Development Experience

Developers work within a single repository, reducing setup complexity and simplifying onboarding.

### Centralized Documentation

Architecture diagrams, ADRs, API documentation and development guides remain synchronized with the codebase.

### Simplified CI/CD

A single repository allows centralized automation through GitHub Actions while enabling independent builds per service.

### Shared Standards

All services follow the same:

* Coding standards
* Testing strategy
* Security guidelines
* Logging conventions
* API design principles

### Better Visibility

Changes across services are easier to review because they remain in the same repository.

### Easier Local Development

Developers can start the complete platform using Docker Compose without cloning multiple repositories.

---

# Consequences

Positive:

* Single source of truth
* Easier dependency management
* Consistent architecture
* Simpler documentation
* Easier code reviews
* Better onboarding
* Shared tooling

Negative:

* Repository size will increase over time.
* CI pipelines require selective execution to avoid unnecessary builds.
* Clear ownership boundaries must be maintained.
* Strict architectural discipline is required to prevent service coupling.

---

# Alternatives Considered

## Multi-Repository

Advantages

* Independent versioning
* Independent permissions
* Smaller repositories
* Independent release cycles

Disadvantages

* More operational complexity
* Harder local development
* Duplicated documentation
* Increased maintenance overhead
* More complex dependency management

Given the expected size of the project and its educational and portfolio goals, these disadvantages outweigh the benefits.

---

# Decision Outcome

The Healthcare Platform will use a **Monorepo** architecture.

Each service will remain independently deployable while sharing common engineering standards, documentation and infrastructure.

Future architectural decisions will build upon this repository strategy.

---

# Related ADRs

* ADR-002: Clean Architecture
* ADR-003: Spring Boot
* ADR-004: PostgreSQL
* ADR-005: JWT Authentication
* ADR-006: Docker
* ADR-007: Event-Driven Communication
