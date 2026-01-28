plugins {
  base
  idea
  application

  // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
  alias(libs.plugins.kotlin.jvm)
}



application {
  mainClass = "io.github.bstdoom.tagessieg.MainKt"
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

dependencies {
  implementation(libs.cli.clikt)
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

tasks.jar {
  manifest {
    attributes["Main-Class"] = application.mainClass
  }
  from({
    configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
  })
  duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
