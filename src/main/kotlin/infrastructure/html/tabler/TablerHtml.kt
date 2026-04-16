package io.github.bstdoom.tagessieg.infrastructure.html.tabler

import io.github.bstdoom.tagessieg.infrastructure.Defaults
import io.github.bstdoom.tagessieg.infrastructure.html.*
import io.github.bstdoom.tagessieg.infrastructure.now
import kotlinx.html.*
import kotlinx.html.stream.createHTML

data class TablerHtml(
  val icon: String? = null,
  val title: String,
  val description: String,
  val navigationBar: NavigationBar = NavigationBar(title = title, icon = icon, date = now()),
  val pageHeader: PageHeader = PageHeader(title = title, subtitle = description),
  val bodyElements: List<Renderable> = emptyList(),
) {

  companion object {
    private fun pageBody(elements: List<Renderable>) = Renderable { receiver ->
      with(receiver) {
        div("page-body") {
          elements.forEach { it.render(this) }
        }
      }
    }
  }

  val html by lazy {
    "<!DOCTYPE html>\n" + createHTML().html {
      lang = Defaults.LOCALE.language
      head {
        charset()
        viewport()
        favicon("⚽")
        title("Tagessieg Statistik", "Statistiken zu KO2 Tagessieg")

        cssHref(TABLER_CSS)

        style {
          unsafe {
            +"""
            .chart-box {
                min-height: 300px;
            }

            .subnote {
                font-size: 0.875rem;
                color: var(--tblr-secondary);
            }
          """.trimIndent()
          }
        }
      }
      body {
        div("page") {
          navigationBar.render(this)

          div("page-wrapper") {
            pageHeader.render(this)
            pageBody(bodyElements).render(this)
          }
        }

        scriptHref(APEXCHARTS_JS)
        scriptHref(TABLER_JS)
      }
    }
  }
}
