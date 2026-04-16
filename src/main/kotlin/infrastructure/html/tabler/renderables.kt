package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Renderable
import kotlinx.html.FlowContent
import kotlinx.html.div

fun divider(text: String, container: Boolean = true): Renderable = Renderable { receiver ->
  fun FlowContent.divider() = div("hr-text") {
    +text
  }

  if (container) {
    container(receiver) {
      divider()
    }
  } else {
    receiver.divider()
  }
}
