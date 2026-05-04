plugins {
  base
  idea

  id("org.jetbrains.kotlin.multiplatform")
  alias(libs.plugins.ktx.serialization)
  alias(libs.plugins.ktx.dataframe)
}

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

tasks.withType<Test>().configureEach {
  useJUnitPlatform()
  workingDir = rootProject.projectDir
}
