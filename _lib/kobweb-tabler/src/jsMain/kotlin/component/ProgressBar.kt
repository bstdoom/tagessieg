package io.toolisticon.kobweb.tabler.component

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.modifiers.attr
import io.toolisticon.kobweb.tabler.style.TablerStyles
import org.jetbrains.compose.web.dom.Div

@Composable
fun ProgressBar(progressPercent: Int, progressClass: String) {
  Div(TablerStyles.progress) {
    Div(TablerStyles.progressBar(progressClass), attrs = {
      attr("style", "width: ${progressPercent}%")
      attr("role", "progressbar")
      attr("aria-valuenow", progressPercent.toString())
      attr("aria-valuemin", "0")
      attr("aria-valuemax", "100")
    })
  }
}
