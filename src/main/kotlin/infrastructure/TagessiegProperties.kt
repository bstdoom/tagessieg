package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.model.type.LocalDateRange
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.nio.file.Path
import java.util.*
import kotlin.io.path.exists
import kotlin.io.path.inputStream

@Serializable
data class TagessiegProperties(
  @Contextual
  val indexHtml: Path,
  @Contextual
  val mainCsv: Path,
  @Contextual
  val testCsv: Path,
  val configuration: Configuration
) {

  @Serializable
  data class Configuration(
    val activeDateRange: LocalDateRange.ByDate
  ) {

  }

  companion object {
    private const val APPLICATION_PROPERTIES_FILE = "application.properties"

    internal data object PropertyKeys {
      const val DOCS_INDEX = "tagessieg.docs.index"
      const val DATA_CSV_MAIN = "tagessieg.data.matches.main"
      const val DATA_CSV_TEST = "tagessieg.data.matches.test"
      const val CONFIG_DATE_UPPER_BOUND = "tagessieg.config.daterange"
    }

    fun Properties.getRequired(key: String): String = getProperty(key) ?: throw IllegalStateException("Property '${key}' is required in gradle.properties")

    fun load(rootPath: Path? = null): TagessiegProperties {

      val props = if (rootPath != null) {
        val file = rootPath.resolve(APPLICATION_PROPERTIES_FILE)
        if (file.exists()) {
          Properties().apply { load(file.inputStream()) }
        } else null
      } else {
        val inputStream = Companion::class.java.classLoader.getResourceAsStream(APPLICATION_PROPERTIES_FILE)
        if (inputStream != null) {
          Properties().apply { load(inputStream) }
        } else null
      }

      requireNotNull(props) { "application.properties is missing" }

      val configDateRange = props.getProperty(PropertyKeys.CONFIG_DATE_UPPER_BOUND)
      val (start, end) = configDateRange.split(",", limit = 2)

      val resolveRoot = rootPath ?: Path.of(".")

      return TagessiegProperties(
        indexHtml = resolveRoot.resolve(props.getProperty(PropertyKeys.DOCS_INDEX) ?: "index.html"),
        mainCsv = resolveRoot.resolve(props.getRequired(PropertyKeys.DATA_CSV_MAIN)),
        testCsv = resolveRoot.resolve(props.getRequired(PropertyKeys.DATA_CSV_TEST)),
        configuration = Configuration(
          activeDateRange = LocalDateRange.ByDate(LocalDate.parse(start), LocalDate.parse(end), false)
        )
      )
    }
  }
}
