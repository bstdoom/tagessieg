package io.github.bstdoom.tagessieg.infrastructure.serialization

import kotlinx.serialization.json.*

class JsonObjBuilder {
  private val map = mutableMapOf<String, JsonElement>()

  infix fun String.to(value: Any?) {
    map[this] = value.toJson()
  }

  fun build(): JsonObject = JsonObject(map)
}

fun jsonObj(block: JsonObjBuilder.() -> Unit): JsonObject = JsonObjBuilder().apply(block).build()

fun jsonArr(vararg values: Any?): JsonArray = JsonArray(values.map { it.toJson() })

/* ---------- Helpers ---------- */

fun series(name: String, vararg data: Number): JsonObject = series(name, data.toList())

fun series(name: String, data: List<Number>): JsonObject = jsonObj {
  "name" to name
  "data" to JsonArray(data.map { it.toJson() })
}

/* ---------- Conversion ---------- */

fun Any?.toJson(): JsonElement = when (this) {
  null -> JsonNull
  is JsonElement -> this

  is String -> JsonPrimitive(this)
  is Number -> JsonPrimitive(this)
  is Boolean -> JsonPrimitive(this)

  is List<*> -> JsonArray(this.map { it.toJson() })
  is Array<*> -> JsonArray(this.map { it.toJson() })

  is Map<*, *> -> JsonObject(
    this.entries.associate { (k, v) ->
      k.toString() to v.toJson()
    }
  )

  else -> JsonPrimitive(this.toString())
}
