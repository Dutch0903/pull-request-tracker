# PR Tracker - Architecture

This project follows **Hexagonal Architecture** (also known as Ports & Adapters), structured around three zones: domain, application, and adapters.

## Package Structure

```
com.prtracker
├── <bounded-context>/
│   ├── domain/               # Business rules, entities, value objects, ports (interfaces)
│   ├── application/          # Use cases — orchestrates domain and ports
│   └── adapter/
│       ├── in/               # Incoming adapters (CLI, scheduler)
│       └── out/              # Outgoing adapters (GitHub API, persistence)
└── shared/                   # Shared kernel (cross-context value objects, CLI infrastructure)
```

Current bounded contexts: `coderepository`, `pullrequest`, `token`.

## Dependency Rule

```
adapter/in → application → domain ← adapter/out
```

- Adapters depend on the application and domain — never the reverse.
- The domain has zero dependencies on anything outside itself.
- Write ports (repositories, external services) live in the domain; read ports (projections) live in `application/query/port/`. All port implementations live in adapters.

---

## Layer Responsibilities

### Domain

The domain contains everything a business expert could describe without mentioning technology.

**Contains:**
- Entities and value objects with their invariants
- Domain services for logic that spans multiple entities
- Write port interfaces (what the domain *needs* from the outside world — repositories, external services)
- Domain exceptions

**Rule:** No imports from `application`, `adapter`, or any framework. Pure Java.

### Application

The application layer contains use cases. It orchestrates the domain and calls ports, but holds no business rules itself.

**Contains:**
- Command handlers (state-changing operations)
- Query handlers (read operations)
- DTOs for input/output across the use-case boundary
- Read ports (`query/port/`) — interfaces for querying read models/projections that return application-layer DTOs, not domain objects

**Rule:** Knows about ports and domain objects. Does not know about adapters or delivery mechanisms.

### Adapters

Adapters translate between the outside world and your application.

- **Incoming (`adapter/in`)**: Trigger use cases — CLI dialogs, schedulers, REST controllers.
- **Outgoing (`adapter/out`)**: Implement ports — GitHub API client, in-memory repositories, file storage.

**Rule:** Adapters may depend on frameworks and infrastructure. They must not contain business logic.

---

## Where Does Logic Belong?

See [PLACEMENT-GUIDE.md](PLACEMENT-GUIDE.md) for the full checklist with examples.

---

## Example Flows

### Write: Adding a repository

```
CLI dialog (adapter/in)
  → CreateCodeRepository.execute(dto)       [application/command]
    → CodeRepositoryReferenceParser.parse() [application/parser — input normalisation]
    → new CodeRepository(...)               [domain/model]
    → CodeRepositoryDomainService.add()     [domain/service — enforces invariants]
      → CodeRepositoryRepository.save()     [domain/port → adapter/out/persistence]
```

### Read: Listing repositories

```
CLI view (adapter/in)
  → GetRecentCodeRepositories.execute()     [application/query]
    → CodeRepositoryReadPort.findAllAsViews() [domain/port → adapter/out/persistence]
```

### Scheduled: Checking repositories

```
Scheduler (adapter/in)
  → CheckRepositories.execute()             [application/command]
    → RepositoryCheckerPort.check(repo)     [domain/port → FetchRepositoryPullRequests]
      → RepositorySynchronizerPort.synchronize() [domain/port → GitHubRepositorySynchronizer]
      → PullRequestRepository.save()        [domain/port → adapter/out/persistence]
```

---

## Further Reading

- [Hexagonal Architecture — Alistair Cockburn](https://alistair.cockburn.us/hexagonal-architecture/)
- [Domain-Driven Design](https://en.wikipedia.org/wiki/Domain-driven_design)
- [CQRS — Martin Fowler](https://martinfowler.com/bliki/CQRS.html)
