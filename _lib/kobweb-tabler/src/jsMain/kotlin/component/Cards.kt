package io.toolisticon.kobweb.tabler.component

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun Cards(content: @Composable () -> Unit) {
  Div(TablerStyles.rowCards) {
    content()
  }
}

@Composable
fun ChartCard(
  title: String,
  chartId: String,
  minHeightPx: Int = 300,
  chart: @Composable () -> Unit,
) {
  Div(TablerStyles.card) {
    Div(TablerStyles.cardHeader) {
      H3(TablerStyles.cardTitle) { Text(title) }
    }

    Div(TablerStyles.cardBody) {
      Div(
        attrs = {
          attr("id", chartId)
          style {
            property("min-height", "${minHeightPx}px")
          }
        }
      ) {
        chart()
      }
    }
  }
}

@Composable
fun CardLabel(text: String) {
  Span(TablerStyles.subheader) {
    Text(text)
  }
}

@Composable
fun CardBodyText(text: String) {
  P(TablerStyles.textSecondaryM0) {
    Text(text)
  }
}
