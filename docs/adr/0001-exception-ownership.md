# ADR 0001: Exception Ownership

**Date:** 2026-06-15
**Status:** Accepted

## Context

Each architectural layer has a distinct responsibility. Exception types should be owned by the layer that defines the contract they represent. Without clear ownership, exceptions leak across layer boundaries — adapters couple to domain internals, use case contracts become undefined, and port failure modes become invisible.

## Decision

**Rule 1 — Domain exceptions may only exist if thrown by the domain layer**

A class in `domain/exception/` may only exist if it is thrown by a domain model, value object, or domain service enforcing a business invariant. Exceptions that represent infrastructure failures or application-layer concerns must not live in `domain/exception/`. Currently the package is empty because no domain model enforces invariants through named exceptions, but the rule permits them when they do — for example, a future `PullRequest` model that throws a domain exception when a PR invariant is violated.

**Rule 2 — Ports own their failure contract**

An application-layer port (interface) owns its failure contract. The exception type is defined co-located with the port interface, not in a shared `exception` package. Infrastructure adapters implement the port and must honor its contract — they catch their own internal failures and throw the port's declared exception before leaving the adapter boundary.

```
application/provider/
  TokenInfoProvider.java      ← port interface
  TokenInfoException.java     ← port failure contract
```

`TokenInfoException` does not extend `ApplicationException`. It is a port-contract exception, not a use-case exception. The use case catches and wraps it.

This is valid under clean architecture: the infrastructure layer depends on the application layer (it implements application interfaces), so throwing an application-layer type does not violate the dependency rule.

**Rule 3 — Use cases own one flat exception per use case**

Each use case that can fail has one flat exception class extending `ApplicationException`. No subclasses unless a caller needs to branch on failure reason.

Use cases throw their exception directly for business rules they enforce themselves. For failures from an external call (a port), they catch the port exception and wrap it, preserving the cause chain. The try-catch scope covers only the external call — not the full method body — so unexpected errors propagate as-is.

```java
// Business rule — throw directly
if (tokenRepository.existsByName(name)) {
    throw new CreateTokenException("Token already exists with name: " + name);
}

// Port call — catch and wrap
try {
    TokenInfo info = tokenInfoProvider.fetchTokenInfo(platform, value);
    tokenRepository.save(...);
} catch (TokenInfoException e) {
    throw new CreateTokenException(e.getMessage(), e);
}
```

## Consequences

- Adapters depend only on `application/exception/*` to handle use case failures — no domain imports required.
- Use case contracts are explicit and stable; the domain can evolve without affecting callers.
- Port failure modes are visible and co-located with the port definition.
- The cause chain is preserved throughout, so the original failure is available in logs.
- Tests assert on application exception types, not domain or port internals.
