# ADR 005: Static Page Generation with kotlinx.html and reveal.js

## Status
Accepted

## Context
Previously, we decided to use Kobweb for generating the statistics site (see [ADR 001](001_basic-architecture.md)). However, the multi-platform approach and Kobweb's complexity are more than what is needed for this project's simple requirements. We want a simpler, more lightweight way to generate a single-page statistics view.

## Decision
We will drop the multi-platform/kobweb approach. Instead, the application will use:
1.  **Clikt commands**: To trigger the generation process.
2.  **kotlinx.html**: For type-safe HTML generation within the Kotlin CLI.
3.  **reveal.js**: To provide a slide-based presentation of the statistics, resulting in a single static `index.html` file.

This simplifies the build process and removes the need for a full web framework.

## Consequences
*   **Reduced Complexity**: No more multi-platform setup or Kobweb dependencies.
*   **Faster Builds**: Generating a single HTML file is significantly faster than building a Kobweb site.
*   **Simplified Deployment**: Only a single `index.html` (plus potentially some assets) needs to be pushed to GitHub Pages.
*   **Easier Maintenance**: The UI logic is now part of the standard Kotlin JVM codebase.
