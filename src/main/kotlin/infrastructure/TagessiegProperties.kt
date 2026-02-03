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

    fun read(rootPath: Path? = null): TagessiegProperties {
      val props = Properties()
      val propertyFileName = APPLICATION_PROPERTIES_FILE

      val loaded = if (rootPath != null) {
        val file = rootPath.resolve(propertyFileName)
        if (file.exists()) {
          file.inputStream().use { props.load(it) }
          true
        } else false
      } else {
        val inputStream = Companion::class.java.classLoader.getResourceAsStream(propertyFileName)
        if (inputStream != null) {
          inputStream.use { props.load(it) }
          true
        } else false
      }

      if (!loaded) {
        // Fallback for tests or when application.properties is missing
        props.setProperty(PropertyKeys.DOCS_INDEX, "index.html")
        props.setProperty(PropertyKeys.DATA_CSV_MAIN, "data/matches.csv")
        props.setProperty(PropertyKeys.DATA_CSV_TEST, "data/matches-test.csv")
        props.setProperty(PropertyKeys.CONFIG_DATE_UPPER_BOUND, "2023-01-01,2026-12-31")
      }

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
