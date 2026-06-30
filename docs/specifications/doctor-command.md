# Doctor Command Specification

- **Document:** SPEC-001
- **Status:** Accepted
- **Version:** 1.0
- **Date:** 2026-06-30
- **Owner:** Healthcare Platform Team

---

# 1. Purpose

The `doctor` command validates the local development environment required to build, test, and maintain projects within the Healthcare Platform ecosystem.

Its objective is to detect missing dependencies, incompatible versions, and environment configuration issues before developers begin working.

The command provides a fast and user-friendly diagnostic report that helps developers prepare their machines with minimal effort.

---

# 2. Scope

The command is part of the **Healthcare Platform CLI (hcp-cli)**.

It performs local environment validation only.

The command **does not**:

- Modify the operating system.
- Install missing software.
- Change environment variables.
- Download dependencies.

Its responsibility is limited to inspection and reporting.

---

# 3. Command Syntax

## Basic

```bash
hcp doctor
```

---

## Verbose Mode

```bash
hcp doctor --verbose
```

Displays additional diagnostic information such as executed commands, execution time, and raw command output.

---

## JSON Output

```bash
hcp doctor --json
```

Returns the report in JSON format.

Useful for CI/CD pipelines and automation.

---

## Validate a Specific Tool

```bash
hcp doctor --tool java
```

Checks only the specified tool.

Supported values:

- java
- git
- gradle
- docker

---

# 4. Functional Requirements

The command shall validate:

| Tool | Required |
|------|----------|
| Operating System | Yes |
| Java | Yes |
| Git | Yes |
| Gradle | Yes |
| Docker | Yes |

Future versions may include:

- Maven
- Node.js
- npm
- kubectl
- Helm
- Terraform
- AWS CLI
- Azure CLI

---

# 5. Validation Rules

## Java

Checks:

- Installed
- Version
- Version >= 21

Command executed:

```text
java -version
```

---

## Git

Checks:

- Installed
- Version

Command executed:

```text
git --version
```

---

## Gradle

Checks:

- Installed
- Version

Command executed:

```text
gradle --version
```

---

## Docker

Checks:

- Installed
- Docker daemon available

Commands executed:

```text
docker --version

docker info
```

---

# 6. Output Format

Example:

```text
Healthcare Platform Doctor
══════════════════════════════════════

Operating System

✔ Windows 11

Java

✔ Installed
Version: 21.0.11

Git

✔ Installed
Version: 2.49.0

Gradle

✔ Installed
Version: 9.5.1

Docker

✔ Installed
Version: 28.3.2

══════════════════════════════════════

Environment Status

✔ READY
```

---

# 7. Failure Output

Example:

```text
Docker

✘ Not Installed

Documentation

https://docs.docker.com/get-docker/
```

---

# 8. Exit Codes

| Code | Meaning |
|------|---------|
| 0 | Environment is valid |
| 1 | One or more required tools are missing |
| 2 | Invalid command usage |
| 3 | Unexpected execution error |

These exit codes enable integration with CI/CD pipelines.

---

# 9. Architecture

The implementation follows the architecture defined in:

- ADR-002 – Clean Architecture
- ADR-004 – hcp-cli Architecture
- ADR-005 – Dependency Injection and Composition Root

The command must never access infrastructure directly.

The dependency flow shall be:

```text
DoctorCommand
        │
        ▼
RunDoctorUseCase
        │
        ▼
ToolChecker
        │
        ▼
CommandExecutorPort
        │
        ▼
ProcessCommandExecutor
```

---

# 10. Non-Functional Requirements

The command should:

- Execute in less than 2 seconds on a typical developer machine.
- Produce deterministic output.
- Support Windows, Linux, and macOS.
- Be easily extensible.
- Be fully unit tested.

---

# 11. Extensibility

New tool validations shall be added by implementing the `ToolChecker` interface.

No modifications to `RunDoctorUseCase` should be required.

The implementation should comply with the Open/Closed Principle.

---

# 12. Testing Strategy

The implementation shall include:

## Unit Tests

- RunDoctorUseCase
- JavaChecker
- GitChecker
- GradleChecker
- DockerChecker

Infrastructure shall be mocked.

No unit test should execute real system commands.

---

## Integration Tests

Integration tests may execute real commands on supported operating systems.

These tests should remain optional and independent of unit tests.

---

# 13. Future Enhancements

Possible future features include:

- Colorized terminal output
- Environment scoring
- Automatic fix suggestions
- Installation links
- Plugin-based tool validation
- CI/CD integration
- HTML report generation
- Markdown report generation
- JSON export
- YAML export
- Telemetry (optional)

---

# 14. Acceptance Criteria

The implementation is considered complete when:

- `hcp doctor` executes successfully.
- Java validation works.
- Git validation works.
- Gradle validation works.
- Docker validation works.
- The report is readable.
- Exit codes are implemented.
- Unit tests pass.
- Build succeeds.
- Documentation is updated.
