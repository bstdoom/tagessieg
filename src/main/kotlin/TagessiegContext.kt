package io.github.bstdoom.tagessieg

import io.github.bstdoom.tagessieg.infrastructure.EchoFormat
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

@Serializable
data class TagessiegContext(

  @Contextual
  val rootDir: Path = Path.of("."),

  @Contextual
  val workDir: Path,

  val format: EchoFormat,

  val dryRun: Boolean
) {
  companion object {
  }


  val dataDir: Path by lazy {
    rootDir.resolve("_data")
  }

  @Transient
  val cs = Charsets.UTF_8

  @Transient
  val quiet = format == EchoFormat.QUIET


  init {
    require(dataDir.exists() && dataDir.isDirectory()) { "dataDir must be absolute" }
  }
}
