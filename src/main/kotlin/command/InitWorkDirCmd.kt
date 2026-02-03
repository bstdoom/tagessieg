package io.github.bstdoom.tagessieg.command

import com.github.ajalt.clikt.core.UsageError
import java.nio.file.Path
import kotlin.io.path.*

class InitWorkDirCmd : SubCommand(name = NAME, help = "Initialize the working directory (copies csv and assets/).") {
  companion object {
    const val NAME = "init"
  }

  override fun run() {
    if (ctx.workDir == Path.of(".") || ctx.workDir.toAbsolutePath() == Path.of("").toAbsolutePath()) {
      throw UsageError("Cannot initialize workDir in current directory")
    }

    if (ctx.workDir.exists() && ctx.workDir.listDirectoryEntries().isNotEmpty()) {
      if (ctx.dryRun) {
        echo("Dry run: would clear ${ctx.workDir}")
      } else {
        ctx.workDir.toFile().deleteRecursively()
      }
    }

    if (!ctx.dryRun) {
      ctx.workDir.createDirectories()

      // copy src/main/resources/static/assets to WORK_DIR/assets
      val assetsDir = ctx.workDir.resolve("assets")
      assetsDir.createDirectories()

      // touch WORK_DIR/index.html
      ctx.workDir.resolve("index.html").createFile()

      // copy src/main/resources/matches.csv to WORK_DIR/data/matches.csv
      val dataDir = ctx.workDir.resolve("data")
      dataDir.createDirectories()
    }

    // Resources copying
    copyResourcesRecursively("/static/assets", ctx.workDir.resolve("assets"))
    copyResource("/matches.csv", ctx.workDir.resolve("data/matches.csv"))

    echo("Initialized workDir at ${ctx.workDir}")
  }

  private fun copyResourcesRecursively(resourcePath: String, target: Path) {
    if (ctx.dryRun) {
      echo("Dry run: would copy resources from $resourcePath to $target")
      return
    }

    // Since we are running in a Gradle project, we can try to find the actual file system path for resources
    // during development. In a real JAR, this would need a different approach (like listing entries in JAR).
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
      // Fallback/Placeholder for JAR execution if needed
      // For now, based on the project structure, it seems to be run via gradle/ide
      echo("Warning: recursive copy not fully implemented for non-file protocols (${url.protocol})", err = true)
    }
  }

  private fun copyResource(resourcePath: String, target: Path) {
    if (ctx.dryRun) {
      echo("Dry run: would copy $resourcePath to $target")
      return
    }

    val stream = javaClass.getResourceAsStream(resourcePath)
    requireNotNull(stream) { "Resource $resourcePath not found" }
    stream.use { input ->
      target.outputStream().use { output ->
        input.copyTo(output)
      }
    }
  }
}
