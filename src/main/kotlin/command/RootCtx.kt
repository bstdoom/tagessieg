package io.github.bstdoom.tagessieg.command

import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties
import io.github.bstdoom.tagessieg.infrastructure.SerializationFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.nio.file.Path

@Serializable
data class RootCtx(
  val format: EchoFormat,
  @Contextual
  val workDir: Path,
  val properties: TagessiegProperties,
) {
  companion object {
  }

  @Transient
  val cs = Charsets.UTF_8
}
