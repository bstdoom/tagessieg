# ADR 006: Provide a Single `kobweb-tabler` Support Library

## Status
Accepted

## Context
The project uses a Kobweb + Tabler + ApexCharts UI pattern that is useful beyond the `tagessieg-site` application. We want a clean separation between the site application and reusable UI support code, while keeping the reusable code opinionated and easy to publish later as an OSS library.

## Decision
We will provide a single support library named `kobweb-tabler` rather than splitting it into multiple published artifacts.

The library will use these coordinates and package conventions:

1.  **Maven coordinates**: `io.toolisticon.kotlin:kobweb-tabler`
2.  **Root Kotlin package**: `io.toolisticon.kobweb.tabler`
3.  **Repository location**: `_lib/kobweb-tabler`

For fast development and fast feedback during implementation, the library will live as a composite build under `_lib/kobweb-tabler` in this repository.

Later, when the library is prepared for publication, it will be moved to its own Maven-oriented project and use [toolisticon/maven-parent-kotlin-base](https://github.com/toolisticon/maven-parent-kotlin-base) as the parent build so that publishing and repository management can be standardized within the organization.

The Kotlin source layout convention is a **MUST**:

- The root class or top-level declarations of the uppermost package must live directly under `src/.../kotlin`.
- We do **not** use Java-style nested package folders for the root package path inside the source tree.
- Example: declarations in `io.toolisticon.kobweb.tabler` must live directly under `src/jsMain/kotlin`, not under `src/jsMain/kotlin/io/toolisticon/kobweb/tabler`.

The library is expected to be opinionated and may provide:

- Tabler-based layout and components
- Silk/CSS-based styling primitives
- ApexCharts integration helpers
- Browser asset loading helpers and other shared UI utilities

## Consequences

- The reusable Kobweb UI support code has a single, stable public identity.
- The site application stays separate from the support library.
- Publication to Maven later is straightforward because the library already has a defined coordinate and package boundary.
- The source layout remains simple and Kotlin-centric, which avoids unnecessary Java-style nesting and keeps package roots easy to find.
