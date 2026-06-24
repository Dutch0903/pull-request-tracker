# ADR 0002: Test Builders

**Date:** 2026-06-24
**Status:** Accepted

## Context

Tests need to construct domain, application, and infrastructure objects with varying field combinations. Constructing objects inline via constructors or factory methods couples tests to constructor signatures and makes setup verbose: every test must specify every field, even those irrelevant to the scenario under test. This noise obscures intent and makes tests brittle when constructors change.

## Decision

Any object used extensively in tests gets a dedicated `*TestBuilder` class in `src/test/java/com/pullrequesttracker/testfixtures/`, mirroring the production package structure across all layers:

```
testfixtures/domain/model/       ← domain aggregates and entities
testfixtures/domain/valueobject/ ← domain value objects
testfixtures/application/dto/    ← application DTOs
testfixtures/infrastructure/     ← infrastructure objects
```

**Rules:**

1. Builders provide sensible defaults for every field so a test only sets what it cares about.
2. Builders expose a static factory method `a<Type>()` and fluent `with<Field>(...)` methods, terminating with `.build()`.
3. Builders are always top-level classes — never inner classes or anonymous types.
4. Tests import builder factory methods statically for readability: `import static ...PullRequestTestBuilder.aPullRequest`.

```java
// Only the field under test is set — everything else is a safe default
PullRequest pr = aPullRequest()
        .withCiStatus(CiStatus.FAILED)
        .build();
```

## Consequences

- Tests express intent clearly: only the field relevant to the scenario is set.
- Tests are resilient to constructor signature changes — only the builder changes, not every test.
- A shared construction vocabulary makes test setup consistent and readable across all layers.
