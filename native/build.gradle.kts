plugins {
  application
  id("org.jetbrains.kotlin.jvm")
  alias(libs.plugins.graalvm.native)
}

val mainClassName = "io.github.bstdoom.tagessieg.TagessiegCli"

repositories {
  mavenCentral()
}

application {
  mainClass.set(mainClassName)
}

kotlin {
  jvmToolchain(21)
}

dependencies {
  implementation(project(":core"))
}

graalvmNative {
  binaries {
    named("main") {
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
}
