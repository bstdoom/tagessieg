package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.nio.file.Path

@Serializable
data class RootCtx(

  @Contextual
  val rootDir: Path,

  @Contextual
  val workDir: Path,

  val format: EchoFormat,

  val properties: TagessiegProperties,
  val dryRun: Boolean,
  val test: Boolean,
) {
  companion object {
  }

  @Transient
  val cs = Charsets.UTF_8

  @Transient
  val quiet = format == EchoFormat.QUIET

  @Transient
  val csvPath = if (!test) properties.mainCsv else properties.testCsv
}
