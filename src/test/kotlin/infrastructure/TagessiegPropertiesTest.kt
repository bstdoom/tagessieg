package io.github.bstdoom.tagessieg.infrastructure

import io.github.bstdoom.tagessieg.infrastructure.TagessiegProperties.Companion.PropertyKeys
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path

class TagessiegPropertiesTest {

  @Test
  fun `should load properties starting with tagessieg`(@TempDir tempDir: Path) {
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.toFile().writeText("""
            tagessieg.data.matches.main=data/matches.csv
            tagessieg.data.matches.test=data/matches-test.csv
            tagessieg.config.daterange=2023-01-01,2023-01-31
            tagessieg.another.prop=value
            other.prop=should.not.be.loaded
        """.trimIndent())

    val properties = TagessiegProperties.read(tempDir)

    assertThat(properties.mainCsv.toString()).endsWith("data/matches.csv")
  }

  @Test
  fun `should throw exception if required property is missing`(@TempDir tempDir: Path) {
    val applicationProps = tempDir.resolve("application.properties")
    applicationProps.toFile().writeText("""
            tagessieg.config.daterange=2023-01-01,2026-12-31
            org.gradle.parallel=true
        """.trimIndent())

    assertThatThrownBy {
      TagessiegProperties.read(tempDir)
    }.isInstanceOf(IllegalStateException::class.java)
      .hasMessageContaining(PropertyKeys.DATA_CSV_MAIN)
  }

  @Test
  fun `should load from real project root`() {
    val properties = TagessiegProperties.read()
    assertThat(properties.mainCsv).isEqualTo(Path.of("./data/matches.csv"))
  }
}
