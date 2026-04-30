---
id: kotlin-projects
title: Kotlin Projects

tags:
  - project
  - kotlin
  - style

agents:
  - all

prio: MUST

refs:
  - title: Kotlin coding conventions
    url: https://kotlinlang.org/docs/coding-conventions.html
    type: public-doc
    check: periodic
  - title: Kotlin coding conventions - verify that your code follows the style guide
    url: https://kotlinlang.org/docs/coding-conventions.html#verify-that-your-code-follows-the-style-guide
    type: public-doc
    check: periodic

status: active
---

# Kotlin Projects

Use this convention for Kotlin project layout, module naming, and package organization.

## Summary

- Kotlin project structure MUST follow the official Kotlin coding conventions where they apply.
- Use the IDE inspection for incorrect formatting to verify conformance.
- Package layout MUST follow the source-organization rules from the Kotlin style guide.

## Package Layout

- Do not introduce intermediate packages between the source root and the actual package path.
- Place source files directly under the source root using the package structure, with no extra package folders before the real package path.
- Keep directories aligned with the full package name below the shared package root.
- For each Kotlin module, create a marker `data object` in the module's package root directly under `src/main/kotlin`.
- Derive the marker name from the module artifactId by removing separators and applying upper camel case.
- For a module with groupId `io.github.xxx.foo` and artifactId `my-Awesome-lib`, the marker MUST be `data object MyAwesomeLib` in `package io.github.xxx.foo`.
- Avoid mixing Kotlin and Java in the same source tree.
- If a module needs both Kotlin and Java, separate them into `src/main/kotlin` and `src/main/java` so each language keeps its own scope.
- In a regular Kotlin project, place source files under `src/main/kotlin`.
- In a Kobweb module, apply the same no-intermediate-package rule under `src/jsMain/kotlin`.
- In a Kotlin Multiplatform project, use only `commonMain`, `jvmMain`, and `jsMain` source sets in this catalogue.
  - Do not introduce `androidMain` or other additional multiplatform source sets in this catalogue unless a convention file explicitly allows them.

## Details

- Keep package names lowercase.
- Avoid underscores in package names.
- Prefer one file per top-level declaration when that improves readability and matches the Kotlin conventions.
- Keep this convention focused on Kotlin-specific code organization; do not duplicate generic formatting rules covered by `.editorconfig` or other shared conventions.
