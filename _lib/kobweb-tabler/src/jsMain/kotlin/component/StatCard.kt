package io.toolisticon.kobweb.tabler.component

import androidx.compose.runtime.Composable
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun StatCard(card: StatCardData) {
  Div(TablerStyles.colSm6Lg3) {
    Div(TablerStyles.card) {
      Div(TablerStyles.cardBody) {
        CardLabel(card.title)
        CardValue(card.value)
        CardBodyText(card.note)

        card.badgeText?.let { badgeText ->
          Div(TablerStyles.mt3) {
            Span(TablerStyles.badge(card.badgeClass)) { Text(badgeText) }
          }
        }

        card.progressPercent?.let { progressPercent ->
          ProgressBar(progressPercent = progressPercent, progressClass = card.progressClass)
        }
      }
    }
  }
}
