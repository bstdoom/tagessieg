package io.toolisticon.kobweb.tabler.style

import org.jetbrains.compose.web.attributes.AttrsScope

fun AttrsScope<*>.classes(vararg names: String) {
  attr("class", names.joinToString(" "))
}
