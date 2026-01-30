package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.serialization.Serializable
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream

@Serializable
data class TagessiegProperties(
  val mainCsvPath: Path
) {

  companion object {
    private const val GRADLE_PROPERTIES_FILE = "gradle.properties"
    private data object Keys {
      const val DATA_CSV_MAIN = "tagessieg.data.csv.main"
    }
    fun read(rootPath: Path = Path.of(".")): TagessiegProperties {
      val props = Properties()
      val gradlePropsFile = rootPath.resolve(GRADLE_PROPERTIES_FILE)
      if (gradlePropsFile.exists()) {
        gradlePropsFile.inputStream().use { props.load(it) }
      }

      val mainCsvPath = props.getProperty(Keys.DATA_CSV_MAIN)
        ?: throw IllegalStateException("Property '${Keys.DATA_CSV_MAIN}' is required in gradle.properties")

      return TagessiegProperties(
        mainCsvPath = Path.of(mainCsvPath)
      )
    }
  }
}
