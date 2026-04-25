package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Renderable
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.role
import kotlinx.html.style

sealed interface Component : Renderable

fun container(receiver: FlowContent, block: FlowContent.() -> Unit) = receiver
  .div("container-xl") {
    block()
  }

fun progressBar(color: String, percent: Int) = Renderable { receiver ->
  receiver.div("progress progress-sm") {
    div("progress-bar bg-$color") {
      role = "progressbar"
      style = "width: ${percent}%"
    }
  }
}

