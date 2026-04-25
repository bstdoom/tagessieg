package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.germanDateTime
import io.github.bstdoom.tagessieg.infrastructure.now
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.header
import kotlinx.html.id
import kotlinx.html.span
import java.time.ZonedDateTime

data class NavigationBar(
  val title: String,
  val icon: String? = null,
  val date: ZonedDateTime = now(),
) : Component {
  override fun render(receiver: FlowContent) = with(receiver) {
    header("navbar navbar-expand-md d-print-none") {
      div("container-xl") {
        div("navbar-brand navbar-brand-autodark pe-0 pe-md-3") {
          h1 {
            +"${icon?.let { "$it " } ?: ""}$title"
          }
        }
        div("navbar-nav flex-row order-md-last") {
          div("nav-item d-flex align-items-center text-secondary") {
            span("ms-1") {
              id = "generated-at"
              +germanDateTime(date)
            }
          }
        }
      }
    }
  }
}
