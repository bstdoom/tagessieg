# Gradle Multiplatform Design Conventions

This project uses Kotlin Multiplatform (KMP) to share code between the command-line interface (JVM) and the statistics website (JS/Browser).

## Project Structure

The project is divided into several modules:

*   **`shared/`**: The core multiplatform module containing data models and logic used by both the CLI and the Site.
*   **`cli/`**: A JVM-only module providing the command-line interface for data extraction and statistics generation.
*   **`site/`**: A JS-only module using Kobweb (Compose HTML) to display statistics in the browser.

## Shared Module Organization

The `shared/` module follows the standard KMP layout:

### `commonMain`
Contains code that is platform-independent. This is where most of the business logic and data models reside.
*   **Location**: `shared/src/commonMain/kotlin/`
*   **Usage**: Models like `Match`, `Game`, and `Winner` are defined here so they can be serialized/deserialized on any platform.
*   **Dependencies**: Only multiplatform libraries (e.g., `kotlinx.serialization`, `kotlinx-datetime`).

### `jvmMain`
Contains JVM-specific implementations or integrations.
*   **Location**: `shared/src/jvmMain/kotlin/`
*   **Usage**: In this project, `jvmMain` is used for CSV serialization because the CSV library used (`kotlinx-serialization-csv`) is currently JVM-specific or requires JVM-specific file I/O for certain operations.
*   **Dependencies**: JVM-only libraries or standard Java APIs.

### `jsMain`
Contains JavaScript-specific implementations.
*   **Location**: `shared/src/jsMain/kotlin/`
*   **Usage**: Used for browser-specific logic or when interacting with JS-specific APIs. In the `shared` module, this is currently minimal as most logic is in `commonMain`.

## Consumer Modules

### `cli` (JVM)
Depends on the `shared` module. It can access both `commonMain` and `jvmMain` code. It handles file system operations and complex data processing that doesn't need to run in a browser.

### `site` (JS/Kobweb)
Depends on the `shared` module. It can access both `commonMain` and `jsMain` code. It focuses on rendering the shared data models using Compose HTML.

## Testing

*   **`commonTest`**: Tests for logic that should behave identically on all platforms.
*   **`jvmTest`**: Tests for JVM-specific logic or tests that use JVM-only testing frameworks like JUnit 5 and AssertJ.
