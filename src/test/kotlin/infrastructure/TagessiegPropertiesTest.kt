package io.github.bstdoom.tagessieg.infrastructure

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

class TagessiegPropertiesTest {

    @Test
    fun `should load properties starting with tagessieg`(@TempDir tempDir: Path) {
        val gradleProps = tempDir.resolve("gradle.properties")
        gradleProps.toFile().writeText("""
            org.gradle.parallel=true
            tagessieg.data.csv.main=docs/resources/main/matches.csv
            tagessieg.another.prop=value
            other.prop=should.not.be.loaded
        """.trimIndent())

        val properties = TagessiegProperties.read(tempDir)

        assertThat(properties.mainCsvPath).isEqualTo(Path.of("docs/resources/main/matches.csv"))
    }

    @Test
    fun `should throw exception if required property is missing`(@TempDir tempDir: Path) {
        val gradleProps = tempDir.resolve("gradle.properties")
        gradleProps.toFile().writeText("""
            org.gradle.parallel=true
        """.trimIndent())

        assertThatThrownBy {
            TagessiegProperties.read(tempDir)
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessageContaining("tagessieg.data.csv.main")
    }

    @Test
    fun `should load from real project root`() {
        // This assumes the test is run from project root and gradle.properties is there
        val properties = TagessiegProperties.read()
        assertThat(properties.mainCsvPath).isEqualTo(Path.of("docs/resources/main/matches.csv"))
    }
}
