# Repository Conventions

- Respect the Kotlin source layout used in this repo.
- For the `site` module, prefer a flat `site/src/jsMain/kotlin` structure for new files.
- Do not reintroduce package-matched folder hierarchies unless explicitly requested.
- Package declarations may differ from file paths; follow the existing Kotlin style in this codebase.
- Keep `core/` and `site/` responsibilities separate:
  - `core/` stays JVM/native and contains the domain, CLI, and data logic.
  - `site/` stays Kobweb/JS and contains UI, pages, and browser-specific code.

## Site structure

* site
  *  src
      * jsMain
          * kotlin
              * AppEntry.kt - `package io.github.bstdoom.tagessieg.site`
              * components/Cards.kt - `package io.github.bstdoom.tagessieg.site.components`
              * pages/Index.kt - `package io.github.bstdoom.tagessieg.site.pages`

- Keep the package declarations above even though the file layout is flat.
- Do not move these files into package-matched directories unless explicitly requested.
