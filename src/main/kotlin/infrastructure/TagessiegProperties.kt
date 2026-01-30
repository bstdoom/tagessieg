package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream

@Serializable
data class TagessiegProperties(
  @Contextual
  val mainCsv: Path,
  val testCsv: Path,
  val configuration: Configuration
) {

  @Serializable
  data class Configuration(
    @Contextual
    val activeDateRange: LocalDateRange.ByDate
  ) {

  }

  companion object {
    private const val GRADLE_PROPERTIES_FILE = "gradle.properties"
    internal data object PropertyKeys {
      const val DATA_CSV_MAIN = "tagessieg.data.matches.main"
      const val DATA_CSV_TEST = "tagessieg.data.matches.test"
      const val CONFIG_DATE_UPPER_BOUND = "tagessieg.config.daterange"
    }

    fun Properties.getRequired(key: String): String = getProperty(key) ?: throw IllegalStateException("Property '${key}' is required in gradle.properties")

    fun read(rootPath: Path = Path.of(".")): TagessiegProperties {
      val props = Properties()
      val gradlePropsFile = rootPath.resolve(GRADLE_PROPERTIES_FILE)
      if (gradlePropsFile.exists()) {
        gradlePropsFile.inputStream().use { props.load(it) }
      }

      val (start,end) = props.getRequired(PropertyKeys.CONFIG_DATE_UPPER_BOUND).split(",", limit = 2)


      return TagessiegProperties(
        mainCsv = Path.of(props.getRequired(PropertyKeys.DATA_CSV_MAIN)),
        testCsv = Path.of(props.getRequired(PropertyKeys.DATA_CSV_TEST)),
        configuration = Configuration(
          activeDateRange = LocalDateRange.ByDate(LocalDate.parse(start), LocalDate.parse(end), false)
        )
      )
    }
  }
}
