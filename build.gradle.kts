plugins {
  base
  alias(libs.plugins.kotlin.multiplatform) apply false
}

allprojects {
  repositories {
    mavenCentral()
    google()
  }
}
