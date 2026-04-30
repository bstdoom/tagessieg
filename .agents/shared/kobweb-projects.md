---
id: kobweb-projects
title: Kobweb Projects

tags:
  - project
  - kotlin
  - kobweb
  - style

agents:
  - all

prio: MUST

refs:
  - title: Kobweb project structure
    url: https://kobweb.varabyte.com/docs/concepts/foundation/project-structure
    type: public-doc
    check: periodic
  - title: Getting Kobweb
    url: https://kobweb.varabyte.com/docs/getting-started/getting-kobweb
    type: public-doc
    check: periodic
  - title: Gradle and Maven Artifacts
    url: https://kobweb.varabyte.com/docs/getting-started/gradle-and-maven-artifacts
    type: public-doc
    check: periodic
  - title: Kotlin coding conventions
    url: https://kotlinlang.org/docs/coding-conventions.html
    type: public-doc
    check: periodic

status: active
---

# Kobweb Projects

Use this convention for Kobweb Kotlin Multiplatform project layout, module naming, and package organization.

## Summary

- Kobweb project modules MUST follow the layout described in the official project-structure guide, with a main application module such as `site`.
- If a project splits frontend code into library modules, keep those module names role-based and close to the guide's examples.
- `site/src/jsMain/kotlin` SHOULD contain `components`, `pages`, and related subfolders such as `layouts`, `sections`, and `widgets`.
- The Kotlin no-intermediate-package rule MUST still be respected under `src/jsMain/kotlin`.
- When a project uses multiple Kobweb application or library modules, each such module SHOULD keep its own package root beneath `src/jsMain/kotlin`.
- Kobweb sites MUST run from Gradle application modules, even though supporting Kobweb libraries can be published and consumed via Maven.
- Kobweb CLI commands SHOULD be available in the agent environment; install the CLI with Homebrew using `brew install varabyte/tap/kobweb` when working on macOS or Linux.

## Module Layout

- Use `site` for the main Kobweb application module.
- Use additional library modules only when the project benefits from splitting pages or components out of the app module.
- Keep module names short and role-based, matching the style of the guide's `site` and `sitelib` examples.
- Keep the generated Yarn lockfile ignored in version control.
- If the Kotlin JS store is moved out of the module root, keep it under `gradle/kotlin-js-store` and configure the lockfile directory in the build script.

```kotlin
rootProject.extensions.getByType<YarnRootExtension>().lockFileDirectory = rootProject.file("gradle/kotlin-js-store")
```

```text
my-project
  sitelib
    build.gradle.kts
    src/jsMain
      kotlin
        components
        pages
  site
    build.gradle.kts
    .kobweb/conf.yaml
    src/jsMain
      kotlin
        components
        pages
```

## Package Layout

- Place frontend code under `src/jsMain/kotlin`.
- Put shared UI code under `components`.
- Put page entry points under `pages`.
- Organize reusable component families under subpackages such as `components/layouts`, `components/sections`, and `components/widgets`.
- Do not introduce intermediate packages between `src/jsMain/kotlin` and the actual package path.
- Keep the package root directly under the source root, with no extra intermediate package folders before the actual package path.
- Keep the directory tree aligned with the package name below the module source root.

## Details

- Follow the Kobweb convention that the frontend structure is centered around `pages` and `components`.
- Treat `components/layouts`, `components/sections`, and `components/widgets` as the default Kobweb organization points for reusable UI.
- If a multi-module Kobweb project splits pages or components across modules, keep the same package discipline in each module.
- Apply the Kotlin package naming rules from the Kotlin projects convention here as well, including the no-intermediate-package rule.
- Use the Kobweb CLI when working in Kobweb projects so the same commands and Gradle delegation path are available to every agent.
