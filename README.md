# PR Tracker

A terminal UI application for tracking pull requests across multiple GitHub repositories.

## Tech Stack

- **Java 25** + **Spring Boot**
- **Tamboui** — terminal UI framework
- **GitHub API** (`org.kohsuke:github-api`) — pull request fetching
- **Jackson** — JSON persistence
- **Lombok** — boilerplate reduction
- **Maven** — build tool

## Architecture

Hexagonal architecture (ports & adapters) organized into three bounded contexts:

- `coderepository` — managing registered repositories
- `pullrequest` — fetching and displaying pull requests
- `token` — managing GitHub API tokens

Each context follows the same structure: `domain` → `application` → `adapter/in` (TUI) + `adapter/out` (persistence, GitHub).

A `shared` module contains the kernel, CLI framework abstractions, and file storage.

## Getting Started

**Prerequisites:** Java 25+, Maven 3.6+

```bash
# Build
./mvnw clean install

# Run
./mvnw spring-boot:run

# Or via JAR
java -jar target/pull-request-tracker-*.jar
```

## Data Storage

Repository, pull request, and token data are persisted as JSON files in the `data/` directory.

## Running Tests

```bash
./mvnw test
```
