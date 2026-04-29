plugins {
  kotlin("multiplatform") version "2.3.20"
  id("org.jetbrains.compose") version "1.10.2"
  id("org.jetbrains.kotlin.plugin.compose") version "2.3.20"
}

group = "io.toolisticon.kotlin"
version = "0.1.0-SNAPSHOT"

repositories {
  mavenCentral()
  google()
}

kotlin {
  js(IR) {
    browser()
  }

  sourceSets {
    val jsMain by getting {
      dependencies {
        implementation("com.varabyte.kobweb:kobweb-core:0.24.0")
        api("com.varabyte.kobweb:kobweb-compose-js:0.24.0")
        implementation("org.jetbrains.compose.runtime:runtime:1.10.2")
        implementation("org.jetbrains.compose.html:html-core-js:1.10.2")
      }
    }
  }
}
