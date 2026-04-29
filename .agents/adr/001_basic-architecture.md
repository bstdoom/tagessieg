# ADR 001: Basic Architecture

## Status
Superseded by [ADR 005](005-static-page-generation.md)

## Context
The `tagessieg` application is a hobby project for tracking match results between two players (`J` and `H`). It needs to be low-maintenance, cost-effective, and easy to use. The primary interactions are submitting match results and viewing statistics.

## Decision
We will implement the application as a serverless system using GitHub infrastructure as the primary runtime and storage:

1.  **Runtime**: Only GitHub Actions will be used for execution.
    *   **Data Extraction**: A GitHub Action is triggered when a "spieltag" issue is submitted. It extracts data from the issue and appends it to a CSV file in the `data/` directory.
    *   **Static Site Generation**: A GitHub Action is triggered by changes to the `data/` directory or manual dispatch to build the statistics site.
2.  **Data Storage**: Data is stored as CSV files in the GitHub repository itself (`data/yyyy.csv`).
3.  **User Interface**:
    *   **Data Entry**: GitHub Issues with a specific template (`.github/ISSUE_TEMPLATE/spieltag.yml`) serve as the input form.
    *   **Statistics View**: A static site hosted on GitHub Pages, generated from the stored data.
4.  **Backend Logic**: A Kotlin-based CLI tool (located in `cli/`) handles both the extraction logic and the site generation coordination.
    *   **CLI Framework**: [Clikt](https://ajalt.github.io/clikt/) is used as the CLI parser. All actions are implemented as `CliktCommand`s.
5.  **Static Site Framework**: ~~[Kobweb](https://kobweb.varabyte.com/) is used as the static site generator for the SPA that serves the statistics.~~ (Superseded by [ADR 005](005-static-page-generation.md))

## Consequences
*   **Zero Hosting Costs**: Using GitHub Actions and GitHub Pages eliminates the need for dedicated servers or database hosting.
*   **Versioned Data**: All match results are stored in Git, providing a full history and easy recovery.
*   **Limited Audience**: The architecture is perfectly suited for the small, fixed target audience (players `J` and `H`).
*   **Latency**: Data updates are not instantaneous as they depend on GitHub Action execution times (usually within a minute).
*   **Simplicity**: The system avoids complex web backends and databases, making it easy for Kotlin developers to maintain using familiar tools.
