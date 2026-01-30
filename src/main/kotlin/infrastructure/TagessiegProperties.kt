package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream

@Serializable
data class TagessiegProperties(
  @Contextual
  val mainCsv: Path,
  val testCsv: Path
) {

  companion object {
    private const val GRADLE_PROPERTIES_FILE = "gradle.properties"
    internal data object PropertyKeys {
      const val DATA_CSV_MAIN = "tagessieg.data.matches.main"
      const val DATA_CSV_TEST = "tagessieg.data.matches.test"
    }

    fun Properties.getRequired(key: String): String = getProperty(key) ?: throw IllegalStateException("Property '${key}' is required in gradle.properties")

    fun read(rootPath: Path = Path.of(".")): TagessiegProperties {
      val props = Properties()
      val gradlePropsFile = rootPath.resolve(GRADLE_PROPERTIES_FILE)
      if (gradlePropsFile.exists()) {
        gradlePropsFile.inputStream().use { props.load(it) }
      }

      return TagessiegProperties(
        mainCsv = Path.of(props.getRequired(PropertyKeys.DATA_CSV_MAIN)),
        testCsv = Path.of(props.getRequired(PropertyKeys.DATA_CSV_TEST))
      )
    }
  }
}
