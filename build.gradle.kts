import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  idea
  application

  // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.serialization)
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

  implementation(libs.kotlinx.html)
  implementation(libs.revealkt.dsl)

  implementation(libs.kotlinx.serialization.core)
  implementation(libs.kotlinx.serialization.csv)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kotlinx.serialization.datetime)

  testImplementation(libs.junit.jupiter.api)
  testRuntimeOnly(libs.junit.jupiter.engine)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation(libs.assertj.core)
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

tasks {

  withType<KotlinCompile> {
    compilerOptions {
      //freeCompilerArgs.add("-Xcontext-parameters")
    }
  }

  jar {
    manifest {
      attributes["Main-Class"] = application.mainClass
    }
    from({
      configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
  }

  test {
    useJUnitPlatform()
  }

  withType<JavaExec> {
    workingDir = rootProject.projectDir
  }

}
