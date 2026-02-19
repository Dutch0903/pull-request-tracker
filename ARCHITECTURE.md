# PR Tracker - Layered Architecture

This project follows a **Layered Architecture** pattern with clear separation of concerns.

## Package Structure

```
com.prtracker
├── Application.java                    # Spring Boot main class
├── presentation/                       # Presentation Layer
│   └── cli/                           # CLI commands (Spring Shell)
├── application/                        # Application Layer
│   ├── service/                       # Application services (orchestration)
│   ├── dto/                           # Data Transfer Objects
│   └── usecase/                       # Use case implementations
├── domain/                            # Domain Layer
│   ├── entity/                        # Domain entities
│   ├── valueobject/                   # Value objects
│   ├── enums/                         # Domain enumerations
│   ├── repository/                    # Repository interfaces (ports)
│   └── service/                       # Domain services (business logic)
└── infrastructure/                    # Infrastructure Layer
    ├── persistence/                   # Repository implementations
    └── external/                      # External service integrations (GitHub API, etc.)
```

## Layer Responsibilities

### 1. Presentation Layer (`presentation/`)
- **Purpose**: Handle user interaction via CLI
- **Contains**: Spring Shell commands
- **Dependencies**: Can depend on Application layer
- **Example**: RegisterRepositoryCommand, ListPullRequestsCommand

### 2. Application Layer (`application/`)
- **Purpose**: Orchestrate use cases and coordinate domain objects
- **Contains**: 
  - Application services (coordinate domain operations)
  - DTOs (data contracts for presentation layer)
  - Use case implementations
- **Dependencies**: Can depend on Domain layer
- **Example**: PullRequestApplicationService, RegisterRepositoryUseCase

### 3. Domain Layer (`domain/`)
- **Purpose**: Core business logic and domain model
- **Contains**:
  - Entities (PullRequest, CodeRepository)
  - Value Objects (PullRequestId)
  - Enumerations (Status)
  - Repository interfaces (ports)
  - Domain services (business rules)
- **Dependencies**: No dependencies on other layers (pure business logic)
- **Example**: PullRequest entity, PullRequestRepository interface

### 4. Infrastructure Layer (`infrastructure/`)
- **Purpose**: Technical implementation details
- **Contains**:
  - Repository implementations (persistence logic)
  - External service clients (GitHub API)
  - Configuration classes
- **Dependencies**: Implements interfaces from Domain layer
- **Example**: JpaPullRequestRepository, GitHubApiClient

## Dependency Rules

```
Presentation → Application → Domain ← Infrastructure
```

- **Presentation** depends on **Application**
- **Application** depends on **Domain**
- **Infrastructure** depends on **Domain** (implements domain interfaces)
- **Domain** has NO dependencies (it's the core)

## Example Flow

1. User executes CLI command → **Presentation Layer** (Command class)
2. Command calls application service → **Application Layer** (Service/UseCase)
3. Application service orchestrates domain logic → **Domain Layer** (Entities, Services)
4. Domain operations persist via repository interface → **Infrastructure Layer** (Repository implementation)

## Benefits

- **Separation of Concerns**: Each layer has a clear responsibility
- **Testability**: Domain logic is isolated and easy to test
- **Flexibility**: Infrastructure can be changed without affecting domain
- **Maintainability**: Clear boundaries make code easier to understand and modify

