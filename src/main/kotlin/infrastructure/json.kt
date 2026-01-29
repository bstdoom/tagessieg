package io.github.bstdoom.tagessieg.infrastructure

import kotlinx.serialization.json.Json

val json = Json {
  useArrayPolymorphism = true
}
