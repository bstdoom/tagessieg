# ADR 004: Use Resources for Production and Test Data

## Status
Accepted

## Context
Previously, data was intended to be stored in a separate `data/` directory at the project root. To simplify the build process, ensure data is bundled with the application logic, and make it easily accessible across different modules (CLI, Site, Shared) via the classpath, a more integrated approach was needed.

## Decision
We will store all match data as CSV files within the `shared` module's resources:

1.  **Production Data**: Stored in `shared/src/main/resources/data.csv`.
2.  **Test Data**: Stored in `shared/src/test/resources/test-data.csv`.

For CSV serialization and encoding, we use [kotlinx-serialization-csv](https://github.com/brudaswen/kotlinx-serialization-csv).

This approach allows:
*   **GitHub Workflows**: The `import-issue` workflow can directly modify the CSV files within the repository structure and commit them back.
*   **Jar Distribution**: Data is automatically bundled into the `shared` JAR, making it available as a resource at runtime.
*   **Consistency**: Test and production data are versioned alongside the code that processes them.

## Consequences
*   **Path Resolution**: When running from the project root (e.g., in GitHub Actions or local Gradle tasks), the relative paths are `shared/src/main/resources/data.csv` and `shared/src/test/resources/test-data.csv`.
*   **Git Management**: Changes to data will appear as modifications to the `shared` module, requiring a commit of these resource files after an import.
*   **Resource Loading**: Code can use `getResource("/data.csv")` for read-only access in production, while the CLI tool uses file system paths to append new data.
