package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.Matches
import java.nio.file.Path
import kotlin.io.path.*

/**
 * This class represents a work directory for managing files and operations.
 *
 * When `run()` it should create a new directory structure and populate it with files.
 *
 * ```
 * WORK_DIR/
 * - assets/
 *   - ... // all files from `src/main/resources/static/assets`
 * - data/
 *   - matches.csv // if matches are provided, creates a new MatchesCsv instance and stores it here.
 *                 // When no matches are provided, copies from `src/main/resources/matches.csv`.
 * - index.html // empty index.html file (will be target for reveal slides).
 * ```
 */
class WorkDirectory(private val workDir: Path, private val matches: Matches? = null) : Runnable {

  init {
      require(workDir.resolve(".git").notExists()) { "Work directory must not be a git repository" }
  }

  override fun run() {
    // 1. Create root directory if it doesn't exist
    if (!workDir.exists()) {
      workDir.createDirectories()
    }

    // 2. Prepare assets directory
    val assetsDir = workDir.resolve("assets")
    if (!assetsDir.exists()) {
      assetsDir.createDirectories()
    }
    copyResourcesRecursively("/static/assets", assetsDir)

    // 3. Prepare data directory and matches.csv
    val dataDir = workDir.resolve("data")
    if (!dataDir.exists()) {
      dataDir.createDirectories()
    }

    val matchesFile = dataDir.resolve("matches.csv")
    if (matches != null) {
      // If matches are provided (e.g., for testing), write them to the file
      MatchesCsv(matchesFile, matches).save()
    } else if (!matchesFile.exists()) {
      // If no matches provided and file doesn't exist, copy default from resources
      copyResource("/matches.csv", matchesFile)
    }

    // 4. Create empty index.html if it doesn't exist
    val indexFile = workDir.resolve("index.html")
    if (!indexFile.exists()) {
      indexFile.createFile()
    }
  }

  /**
   * Copies a single resource from the classpath to the target path.
   */
  private fun copyResource(resourcePath: String, target: Path) {
    val stream = javaClass.getResourceAsStream(resourcePath)
    requireNotNull(stream) { "Resource $resourcePath not found" }
    stream.use { input ->
      target.outputStream().use { output ->
        input.copyTo(output)
      }
    }
  }

  /**
   * Recursively copies resources from a directory in the classpath to a target Path.
   * Handles both standard file system and can be extended for JAR entry listing.
   */
  private fun copyResourcesRecursively(resourcePath: String, target: Path) {
    val url = javaClass.getResource(resourcePath)
    requireNotNull(url) { "Resource directory $resourcePath not found" }

    if (url.protocol == "file") {
      val sourceDir = Path.of(url.toURI())
      sourceDir.walk().forEach { sourcePath ->
        val relativePath = sourceDir.relativize(sourcePath)
        val targetPath = target.resolve(relativePath.toString())
        if (sourcePath.isDirectory()) {
          targetPath.createDirectories()
        } else {
          targetPath.parent.createDirectories()
          sourcePath.copyTo(targetPath, overwrite = true)
        }
      }
    } else {
      // Note: In a production JAR environment, you would typically use a
      // FileSystem provider for the JAR to walk the resource tree.
      System.err.println("Warning: recursive copy not fully implemented for non-file protocols (${url.protocol})")
    }
  }
}
