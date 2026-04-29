# ADR And Test Findings

Date: `2026-04-17`

This note captures findings from a repository review. It is intentionally informational only and does not imply immediate action.

## Test Suite

- `./gradlew test` completed with `97` tests executed, `2` failed, `8` skipped.
- Both failures are in [src/test/kotlin/infrastructure/SerializationFormatTest.kt](/Users/jangalinski/IdeaProjects/bstdoom/tagessieg/src/test/kotlin/infrastructure/SerializationFormatTest.kt).
- The failures appear to be brittle assertions rather than a serialization regression.
- [src/main/kotlin/infrastructure/serialization/SerializationFormat.kt](/Users/jangalinski/IdeaProjects/bstdoom/tagessieg/src/main/kotlin/infrastructure/serialization/SerializationFormat.kt) uses `prettyPrint = true` for JSON.
- The failing assertions expect minified JSON fragments without whitespace.

## Generated Sample

- `GenerateTablerSiteTest` produced a full sample output at [build/html/index.html](/Users/jangalinski/IdeaProjects/bstdoom/tagessieg/build/html/index.html).

## ADR Review

### ADR 001

- Broad direction still fits: Kotlin CLI, GitHub-oriented workflow, static output.
- Several concrete details look stale:
- It mentions `data/yyyy.csv`.
- It mentions a `cli/` location.
- It assumes issue-template and GitHub Action wiring that is not present in the current repository.

### ADR 002

- Partly valid.
- Date conventions appear consistent.
- The rule "user-facing German only" is not consistently enforced in the current code and output.
- Current examples include `Dashboard`, `All your stats in one place`, `League Table`, `To be continued`, `Submit Match`, and `No matches.csv found...`.

### ADR 003

- Still valid.
- AssertJ is used consistently in the tests reviewed.

### ADR 004

- No longer valid.
- It describes a `shared` module and resource-based files like `shared/src/main/resources/data.csv`.
- The current project is single-module and uses `_data/`, `src/main/resources/matches.csv`, and `src/test/resources/matches-test.csv`.

### ADR 005

- Mostly valid, but too narrow in its current wording.
- The repository now contains both a reveal.js path and a Tabler dashboard path.
- The ADR framing of a single reveal.js-based output no longer reflects the full implementation.

## Guideline Drift

- The privacy rule says to use only `J` and `H`.
- Current dashboard output uses `Jan` and `Heiko`.
- The current generated dashboard confirms both language and naming mismatches in actual output.

## Suggested Follow-Up Topics For Later

- Decide whether to update ADRs to match the current implementation or to realign the codebase with the ADRs.
- Fix the brittle JSON assertions in `SerializationFormatTest`.
- Decide whether user-facing text should be fully German across CLI and generated output.
- Decide whether names should be normalized back to `J` and `H` everywhere user-facing.
