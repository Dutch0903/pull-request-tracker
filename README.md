# PR Tracker

A command-line application for tracking pull requests across multiple code repositories. Built with Spring Boot and Spring Shell, following clean layered architecture principles.

## Features

- 📋 Register and manage multiple code repositories
- 🔍 Track pull requests across registered repositories
- 💾 Persistent storage using JSON file-based storage
- ⚡ Scheduled background checks for repository updates
- 🎯 Clean separation of concerns with layered architecture
- 🖥️ Interactive CLI powered by Spring Shell

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.2**
- **Spring Shell 4.0.1** - Interactive CLI framework
- **Jackson** - JSON serialization/deserialization
- **Lombok** - Boilerplate reduction
- **Maven** - Dependency management

## Architecture

This project follows a **Layered Architecture** pattern with clear separation of concerns:

```
Presentation Layer (CLI) 
    ↓
Application Layer (Use Cases)
    ↓
Domain Layer (Business Logic)
    ↑
Infrastructure Layer (Persistence, External APIs)
```

See [ARCHITECTURE.md](ARCHITECTURE.md) for detailed architecture documentation.

## Project Structure

```
src/main/java/com/prtracker/
├── Application.java                    # Spring Boot entry point
├── presentation/cli/                   # CLI commands
├── application/
│   ├── service/                       # Application services
│   ├── dto/                           # Data transfer objects
│   └── usecase/                       # Use case implementations
├── domain/
│   ├── entity/                        # Domain entities (CodeRepository, PullRequest)
│   ├── valueobject/                   # Value objects (CodeRepositoryId, PullRequestId)
│   ├── enums/                         # Domain enumerations (Status)
│   └── repository/                    # Repository interfaces
└── infrastructure/
    ├── persistence/                   # File-based storage implementation
    ├── scheduling/                    # Background job scheduling
    └── external/                      # External API integrations
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd pr-tracker
```

2. Build the project:
```bash
./mvnw clean install
```

### Running the Application

Start the application using Maven:

```bash
./mvnw spring-boot:run
```

Or run the compiled JAR:

```bash
java -jar target/pr-tracker-0.0.1-SNAPSHOT.jar
```

## Usage

Once the application starts, you'll be presented with an interactive shell. Type `help` to see available commands.

### Example Commands

```shell
# Register a new repository
pr-tracker:> register-repository --owner spring-projects --name spring-boot

# List all registered repositories
pr-tracker:> list-repositories

# Check repositories for pull requests
pr-tracker:> check-repositories

# Exit the application
pr-tracker:> exit
```

## Data Storage

Repository data is stored in JSON format in the `data/` directory:

```
data/
└── repositories.json
```

The application automatically loads repositories on startup and persists changes to disk.

## Key Design Patterns

### Use Case Pattern

All application logic is implemented as use cases, inheriting from base classes:

- `AbstractUseCase<T>` - For use cases that return a value
- `AbstractVoidUseCase` - For use cases that return void

Example use cases:
- `InitializeCodeRepositoriesUseCase` - Loads repositories from file on startup
- `PersistCodeRepositoriesUseCase` - Saves repositories to file
- `CheckRepositoriesUseCase` - Checks all repositories for updates

### Repository Pattern

Domain entities are persisted through repository interfaces defined in the domain layer and implemented in the infrastructure layer.

### DTO Mapping

To keep the domain layer free from infrastructure concerns (like Jackson annotations), DTOs and mappers are used in the infrastructure layer to handle serialization.

## Development

### Running Tests

```bash
./mvnw test
```

### Building

```bash
./mvnw clean package
```

The compiled JAR will be available in the `target/` directory.

## Configuration

Application configuration is managed through `application.yaml`:

```yaml
spring:
  application:
    name: pr-tracker
```

## Contributing

1. Follow the layered architecture principles
2. Keep domain logic free from infrastructure dependencies
3. Use the use case pattern for application logic
4. Write tests for new features
5. Follow existing code style and conventions
