plugins {
  base
  idea
  application

  // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
  alias(libs.plugins.kotlin.jvm)
}

application {
  mainClass = "io.gihub.bstdoom.tagessieg.MainKt"
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

dependencies {
  // This dependency is used by the application.
}

testing {
  suites {
    // Configure the built-in test suite
    val test by getting(JvmTestSuite::class) {
      // Use JUnit Jupiter test framework
      useJUnitJupiter("5.12.1")
    }
  }
}

// Apply a specific Java toolchain to ease working on different environments.
java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

