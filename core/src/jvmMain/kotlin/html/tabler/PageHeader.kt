package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.html.Renderable
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h2

data class PageHeader(
  val title: String,
  val subtitle: String,
) : Renderable {
  override fun render(receiver: FlowContent) = with(receiver) {
    div("page-header") {
      div("container-xl") {
        div("row g-2 align-items-center") {
          div("col") {
            h2("page-title") { +title }
            div("text-secondary") { +subtitle }
          }
        }
      }
    }
  }
}
