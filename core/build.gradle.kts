plugins {
  base
  idea

  id("org.jetbrains.kotlin.multiplatform")
  alias(libs.plugins.ktx.serialization)
  alias(libs.plugins.ktx.dataframe)
  alias(libs.plugins.graalvm.native)
}

val mainClassName = "io.github.bstdoom.tagessieg.TagessiegCli"

repositories {
  // Use Maven Central for resolving dependencies.
  mavenCentral()
}

kotlin {
  jvm()
  js(IR) {
    browser()
  }
  jvmToolchain(21)

  sourceSets {
    commonMain.dependencies {
      implementation(libs.ktx.serialization.core)
      implementation(libs.ktx.serialization.json)
      implementation(libs.ktx.serialization.datetime)
    }

    commonTest.dependencies {
      implementation(kotlin("test"))
    }

    jvmMain.dependencies {
      implementation(libs.kt.logging)
      implementation(libs.logback.classic)

      implementation(libs.cli.clikt)

      implementation(libs.ktx.dataframe)
      implementation(libs.ktx.kandy)
      implementation(libs.ktx.html)
      implementation(libs.revealkt.dsl)

      implementation(libs.ktx.serialization.csv)
    }

    jvmTest.dependencies {
      implementation(libs.junit.jupiter.api)
      implementation(libs.junit.jupiter.params)
      implementation(libs.assertj.core)
      implementation(libs.test.instancio)
      runtimeOnly(libs.junit.jupiter.engine)
      runtimeOnly("org.junit.platform:junit-platform-launcher")
    }

    val jvmMain by getting {
      kotlin.srcDir("src/jvmMain/kotlin")
      resources.srcDir("src/main/resources")
    }

    val jvmTest by getting {
      kotlin.srcDir("src/test/kotlin")
      resources.srcDir("src/test/resources")
    }
  }
}

graalvmNative {
  binaries {
    create("main") {
      imageName.set("tagessieg")
      mainClass.set(mainClassName)
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

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
  workingDir = rootProject.projectDir
}
