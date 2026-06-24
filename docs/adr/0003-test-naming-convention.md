# ADR 0003: Test Naming Convention

**Date:** 2026-06-24
**Status:** Accepted

## Context

Consistent test names make it immediately clear what a test covers, what precondition triggered it, and what outcome it asserts — without reading the body. Inconsistent naming (generic `whenCalled` placeholders, mixed `when`/`with` usage, missing conditions) obscures intent and makes test suites harder to scan.

## Decision

### Structure

Test method names follow one of three forms:

**Three-part (default)** — use whenever a non-trivial condition exists:
```
methodName_[when|with]Condition_shouldOutcome
```

**Two-part** — use when adding a condition would be redundant (no meaningful variation):
```
methodName_shouldOutcome
```

**Sequence** — use only when two operations are inseparable (the second literally requires the first):
```
methodName_thenMethodName_shouldOutcome
```

---

### Rules

**`with` vs `when`**

- `with` — the condition describes the **shape of the input data**:
  - `calculate_withEmptyList_shouldReturnAllZeros`
  - `fetch_withUnmappedPlatform_shouldThrowException`

- `when` — the condition describes a **state or behavioural precondition**:
  - `execute_whenTokenNameAlreadyExists_shouldThrowException`
  - `execute_whenNameHasChanged_shouldValidateNameUniqueness`
  - `addReview_whenReviewIsAlreadySubmitted_shouldNotAddReview`

**Ban `whenCalled`**

`whenCalled` adds no information — the method is always called. Drop to two-part when there is no meaningful condition:

| Before | After |
|--------|-------|
| `execute_whenCalled_shouldDeleteToken` | `execute_shouldDeleteToken` |
| `toDomain_whenCalled_shouldMapValuesCorrectly` | `toDomain_shouldMapValuesCorrectly` |

If the condition *is* meaningful but was hidden behind `whenCalled`, name it explicitly:

| Before | After |
|--------|-------|
| `calculate_whenCalled_shouldIgnoreAllNonOpenPullRequests` | `calculate_whenNonOpenPullRequestsArePresent_shouldIgnoreThem` |

**Two-part names**

Two-part is correct when the method has no meaningful varying condition, such as `toString` or `equals` on a fixed value. If a non-trivial input or state makes the outcome possible, that condition belongs in the name:

```
toString_shouldReturnOwnerSlashName          ✓  (no variation possible)
equals_whenIdsHaveSameValue_shouldBeEqual    ✓  (state varies)
calculate_withMoreEntriesThanMaximum_shouldLimitResults  ✓  (condition adds value)
```

**Outcome segment**

Always starts with `should` or `shouldNot`:
```
_shouldReturnAllZeros
_shouldThrowException
_shouldNotAddReview
```

**`then` for sequences**

Only allowed when the second operation requires the first to have run — i.e. there is no way to stub or isolate them:
```
save_thenLoad_shouldReturnSavedData   ✓
```

---

### Summary table

| Scenario | Prefix | Example |
|----------|--------|---------|
| Input is empty / null / blank | `with` | `parse_withNullInput_shouldThrowException` |
| Input has a specific shape | `with` | `fetch_withUnmappedPlatform_shouldThrowException` |
| System is in a specific state | `when` | `execute_whenTokenNotFound_shouldThrowException` |
| Business rule triggers | `when` | `execute_whenNameHasChanged_shouldValidateNameUniqueness` |
| No meaningful condition | *(omit)* | `toString_shouldReturnValue` |
| Two inseparable operations | `then` | `save_thenLoad_shouldReturnSavedData` |

## Consequences

- Test names are scannable without reading the body.
- Failures in CI immediately communicate what condition broke and what was expected.
- `whenCalled` is removed from the vocabulary; authors are forced to name the actual condition.
- The `with`/`when` split makes input-driven and state-driven tests visually distinct.
