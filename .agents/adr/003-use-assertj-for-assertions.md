# ADR 003: Use AssertJ for Assertions

## Status
Accepted

## Context
The project uses JUnit 5 for testing. While JUnit 5 provides built-in assertions, AssertJ offers a more fluent and expressive API for writing assertions, which improves readability and provides better error messages.

## Decision
We will use AssertJ (`org.assertj.core.api.Assertions.assertThat`) as the primary assertion library for all tests in the project. JUnit 5 assertions should be avoided unless there is a specific reason AssertJ cannot be used.

## Consequences
- Improved readability of test code.
- More descriptive error messages when assertions fail.
- Consistent assertion style across the codebase.
