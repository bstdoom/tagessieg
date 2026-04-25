import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.ktx.compose)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.kobweb.application)
}

kotlin {
  configAsKobwebApplication()

  sourceSets {
    commonMain.dependencies {
      implementation(libs.kobweb.core)
    }

    jsMain.dependencies {
      implementation(compose.runtime)
      implementation(compose.html.core)
    }
  }
}
