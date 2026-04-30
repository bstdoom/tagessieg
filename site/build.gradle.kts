import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  alias(libs.plugins.ktx.compose)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.kobweb.application)
}

kobweb {
  pagesPackage = "io.github.bstdoom.tagessieg.site.pages"
}

rootProject.plugins.withType<YarnPlugin> {
  rootProject.extensions.getByType<YarnRootExtension>().lockFileDirectory = rootProject.file("gradle/kotlin-js-store")
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
      implementation("com.varabyte.kobweb:kobweb-compose-js:0.24.0")
      implementation("io.toolisticon.kotlin:kobweb-tabler:0.1.0-SNAPSHOT")
    }
  }
}
