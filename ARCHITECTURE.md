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
- **Packages**: `cli/`, `cli/screen/`
- **Purpose**: Handle user interaction via TUI (Text User Interface)
- **Contains**: TamboUI screen controllers, key bindings, screen definitions
- **Dependencies**: Application layer (queries and commands)
- **Returns/Receives**: Works with DTOs and View models only, never domain entities

### 2. Application Layer (`application/`) - CQRS Split

#### Read Side - Queries
- **Packages**: `query/`, `repository/`, `dto/`
- **Purpose**: Fetch data optimized for display
- **Contains**:
  - `query/` - Query implementations (GetTokensQuery, GetCodeRepositoriesQuery)
  - `repository/` - Read repository interfaces (TokenReadRepository, CodeRepositoryReadRepository)
  - `dto/` - DTOs and View models (TokenView, CodeRepositoryView)
- **Dependencies**: Application layer read repositories only
- **Returns**: Immutable DTOs/Views
- **No Side Effects**: Queries never modify state

#### Write Side - Commands
- **Packages**: `command/`, `dto/` (input DTOs)
- **Purpose**: Execute state-changing operations
- **Contains**: Command implementations (AddTokenCommand, AddCodeRepositoryCommand)
- **Dependencies**: Domain layer repositories and entities
- **Works With**: Rich domain entities with business logic
- **Has Side Effects**: Commands modify state

#### Supporting Packages
- **Package**: `parser/`
- **Purpose**: Parse and validate user input

### 3. Domain Layer (`domain/`)
- **Packages**: `entity/`, `valueobject/`, `enums/`, `repository/`, `service/`, `exceptions/`
- **Purpose**: Core business logic and domain model
- **Contains**:
  - `entity/` - Rich domain entities (Token, CodeRepository, PullRequest)
  - `valueobject/` - Immutable value objects (TokenId, CodeRepositoryIdentifier)
  - `enums/` - Domain enumerations (CodeRepositoryStatus)
  - `repository/` - Repository interfaces for write operations (ports)
  - `service/` - Domain services with complex business logic
  - `exceptions/` - Domain-specific exceptions
- **Dependencies**: **None** - pure business logic
- **Used By**: Commands (write operations)

### 4. Infrastructure Layer (`infrastructure/`)
- **Packages**: `persistence/`, `persistence/dto/`, `persistence/mapper/`, `scheduling/`
- **Purpose**: Technical implementations and external concerns
- **Contains**:
  - `persistence/` - Repository implementations (InMemoryTokenRepository, InMemoryCodeRepositoryRepository)
    - Implements **both** domain (write) and application (read) repository interfaces
  - `persistence/dto/` - Persistence DTOs for serialization
  - `persistence/mapper/` - Mappers between domain entities and persistence DTOs
  - `scheduling/` - Background scheduled tasks
  - `LifeCycleManager` - Application lifecycle hooks (@PostConstruct, @PreDestroy)
- **Dependencies**: Implements interfaces from both Domain and Application layers
- **Key Point**: Single implementation, dual interfaces (read + write)

## Dependency Rules

```
Presentation → Application → Domain ← Infrastructure
```

- **Presentation** depends on **Application**
- **Application** depends on **Domain**
- **Infrastructure** depends on **Domain** (implements domain interfaces)
- **Domain** has NO dependencies (it's the core)

## Example Flows

### Query Flow (Read Operation)
**Example**: User views the dashboard to see all repositories

```
1. User navigates to dashboard
   → DashboardController.populate()
   [Presentation Layer: presentation/cli/screen/]

2. Controller executes query
   → getCodeRepositoriesQuery.execute()
   [Application Layer: application/query/]

3. Query fetches from read repository
   → codeRepositoryReadRepository.findAllAsViews()
   [Application Layer: application/repository/ interface]

4. Infrastructure maps entities to DTOs
   → InMemoryCodeRepositoryRepository.findAllAsViews()
   → Maps List<CodeRepository> to List<CodeRepositoryView>
   [Infrastructure Layer: infrastructure/persistence/]

5. Query returns DTOs to controller
   → Returns List<CodeRepositoryView> (immutable read models)
   [Application Layer: application/dto/]

6. Controller binds DTOs to template
   → Template displays simple data
   [Presentation Layer: presentation/cli/screen/]
```

**Key Points**:
- ✅ No domain entities exposed to presentation
- ✅ Read-optimized DTOs returned
- ✅ No side effects
- ✅ Can be cached independently

### Command Flow (Write Operation)
**Example**: User adds a new token

```
1. User submits token form
   → AddTokenController.save()
   [Presentation Layer: presentation/cli/screen/]

2. Controller executes command with input DTO
   → addTokenCommand.execute(AddTokenDto)
   [Application Layer: application/command/]

3. Command validates input (optional)
   → validate(AddTokenDto)
   [Application Layer: application/command/]

4. Command creates domain entity
   → new Token(TokenId.create(), name, value)
   [Domain Layer: domain/entity/]

5. Command persists via domain repository
   → tokenRepository.save(token)
   [Domain Layer: domain/repository/ interface]

6. Infrastructure handles persistence
   → InMemoryTokenRepository.save(token)
   → Stores in ConcurrentHashMap<TokenId, Token>
   [Infrastructure Layer: infrastructure/persistence/]

7. Controller navigates to success screen
   → navigationRouter.navigateTo(Screen.TOKENS)
   [Presentation Layer: presentation/cli/screen/]
```

**Key Points**:
- ✅ Full access to rich domain entities
- ✅ Business logic in domain layer
- ✅ Has side effects (state change)
- ✅ Can trigger domain events (future)

## Benefits

### Layered Architecture Benefits
- **Separation of Concerns**: Each layer has a clear, single responsibility
- **Testability**: Domain logic is isolated and easy to unit test
- **Flexibility**: Infrastructure can be changed without affecting business logic
- **Maintainability**: Clear boundaries make code easier to understand and modify

### CQRS Benefits
- **Optimized Reads**: Queries return only the data needed for display
- **Optimized Writes**: Commands work with rich domain models and business logic
- **Independent Scaling**: Read and write sides can be scaled independently
- **Type Safety**: Compile-time prevention of queries using write repositories
- **Immutability**: DTOs are immutable records, preventing accidental modifications
- **Performance**: Queries can be cached without affecting command side
- **Clear Intent**: Code clearly shows whether operation reads or writes

### Specific Improvements in This Implementation
1. **No Domain Leakage**: Presentation layer never sees domain entities
2. **Compile-Time Safety**: Type system prevents misuse of repositories
3. **Single Implementation**: Infrastructure implements both interfaces (DRY)
4. **Easy Testing**: 
   - Test queries with simple DTO assertions
   - Test commands with domain logic validation
   - Mock repositories independently
5. **Future-Proof**: Ready for:
   - Query result caching
   - Command auditing
   - Event sourcing
   - Separate read/write databases (if needed)

## Design Patterns Used

- **Layered Architecture**: Clear separation of presentation, application, domain, infrastructure
- **CQRS**: Command Query Responsibility Segregation for read/write separation
- **Repository Pattern**: Abstract data access behind interfaces
- **DTO Pattern**: Data Transfer Objects for cross-layer communication
- **Factory Pattern**: Static factory methods for DTO/Entity conversion
- **Command Pattern**: Encapsulate operations as command objects
- **Dependency Inversion**: High-level modules don't depend on low-level modules
- **Interface Segregation**: Separate interfaces for read and write operations

## Key Files

### Base Classes
- `application/query/Query.java` - Base class for all queries
- `application/query/VoidQuery.java` - Query with no input parameter
- `application/command/Command.java` - Base class for all commands
- `application/command/VoidCommand.java` - Command with no input/output

### Example Query
- `application/query/GetCodeRepositoriesQuery.java`
- `application/repository/CodeRepositoryReadRepository.java`
- `application/dto/CodeRepositoryView.java`

### Example Command
- `application/command/AddCodeRepositoryCommand.java`
- `domain/repository/CodeRepositoryRepository.java`
- `domain/entity/CodeRepository.java`

### Infrastructure Implementation
- `infrastructure/persistence/InMemoryCodeRepositoryRepository.java` (implements both interfaces)

## Further Reading

- **CQRS Pattern**: [Martin Fowler - CQRS](https://martinfowler.com/bliki/CQRS.html)
- **Clean Architecture**: [Robert C. Martin - Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- **DDD**: [Domain-Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design)

