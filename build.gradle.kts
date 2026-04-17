import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  base
  idea
  application

  // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
  alias(libs.plugins.kt.jvm)
  alias(libs.plugins.ktx.serialization)
  alias(libs.plugins.ktx.dataframe)
  alias(libs.plugins.graalvm.native)
}

application {
  mainClass = "io.github.bstdoom.tagessieg.TagessiegCli"
}

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

dependencies {
  // core
  implementation(libs.kt.logging)
  implementation(libs.logback.classic)

  // cli
  implementation(libs.cli.clikt)

  // stats
  implementation(libs.ktx.dataframe)
  implementation(libs.ktx.kandy)

  // html
  implementation(libs.ktx.html)
  implementation(libs.revealkt.dsl)

  // serialization
  implementation(libs.ktx.serialization.core)
  implementation(libs.ktx.serialization.csv)
  implementation(libs.ktx.serialization.json)
  implementation(libs.ktx.serialization.datetime)

  // test
  testImplementation(libs.junit.jupiter.api)
  testRuntimeOnly(libs.junit.jupiter.engine)
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  testImplementation(libs.assertj.core)
  testImplementation(libs.test.instancio)
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

graalvmNative {
  binaries {
    named("main") {
      imageName.set("tagessieg")
      mainClass.set(application.mainClass)
      javaLauncher.set(javaToolchains.launcherFor {
        languageVersion.set(JavaLanguageVersion.of(21))
      })
      buildArgs.add("-H:+AddAllCharsets")
      buildArgs.add("--initialize-at-build-time=kotlin.DeprecationLevel")
    }
  }
  metadataRepository {
    enabled.set(true)
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
