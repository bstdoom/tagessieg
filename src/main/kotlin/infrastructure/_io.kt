@file:OptIn(ExperimentalPathApi::class)

package io.github.bstdoom.tagessieg.infrastructure

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*


fun Path.copyToRecursive(target: Path) {
  require(isDirectory() && exists()) { "Source path must be an existing directory." }
  val targetExists = target.exists()
  if (!targetExists) {
    target.createDirectories()
  }

  require(target.isDirectory()) { "Source path must be a directory" }
  require(this != target) { "Source and target directories must be different" }
  require(this.resolve(".git").notExists()) { "Source directory must not be a git repository" }
  require(target.resolve(".git").notExists()) { "Target directory must not be a git repository" }

  if (target.listDirectoryEntries().isNotEmpty()) {
    target.deleteRecursively()
    this.copyTo(target)
  }

  this.copyToRecursively(
    target,
    followLinks = false,
    overwrite = true
  )
}

fun Path.tree(): String {
  val builder = StringBuilder()
  fun recurse(path: Path, prefix: String = "", isLast: Boolean = true) {
    val connector = if (isLast) "└── " else "├── "
    if (builder.isEmpty()) {
      builder.appendLine(prefix +  path.fileName)
    } else {
      builder.appendLine(prefix + connector + path.fileName)
    }

    if (path.isDirectory()) {
      val children = Files.list(path).use { it.sorted().toList() }

      children.forEachIndexed { index, child ->
        val newPrefix = prefix + if (isLast) "    " else "│   "
        recurse(child, newPrefix, index == children.lastIndex)
      }
    }
  }
  recurse(this)
  return builder.toString()
}
