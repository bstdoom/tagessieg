package io.toolisticon.kobweb.tabler.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.ui.modifiers.attr
import io.toolisticon.kobweb.tabler.asset.ensureApexChartsLoaded
import io.toolisticon.kobweb.tabler.style.TablerStyles
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

@Composable
fun ApexChart(
  chartId: String,
  options: Any,
  minHeightPx: Int,
  loadingMessage: String = "Loading chart...",
) {
  val mounted = remember { mutableStateOf(false) }
  val failureMessage = remember { mutableStateOf<String?>(null) }

  Div(attrs = {
    attr("id", chartId)
    attr(
      "style",
      "min-height: ${minHeightPx}px; display: flex; align-items: center; justify-content: center;"
    )
  })

  LaunchedEffect(chartId, options) {
    runCatching {
      ensureApexChartsLoaded()

      var element: HTMLElement? = null
      var attempts = 0
      while (element == null && attempts < 10) {
        element = document.getElementById(chartId) as? HTMLElement
        if (element != null) break
        delay(16)
        attempts++
      }

      val target = element ?: throw IllegalStateException("Chart container was not mounted for $chartId")
      val chart = ApexCharts(target, options)
      chart.render()
      mounted.value = true
    }.onFailure { error ->
      failureMessage.value = error.message ?: error::class.simpleName ?: "Unknown chart failure"
    }
  }

  DisposableEffect(chartId) {
    onDispose {
      if (mounted.value) {
        (document.getElementById(chartId) as? HTMLElement)?.let { it.innerHTML = "" }
      }
    }
  }

  when {
    failureMessage.value != null -> {
      Div(TablerStyles.alertDangerMb0) {
        Text("Chart failed to render: ${failureMessage.value}")
      }
    }
    !mounted.value -> {
      Div(TablerStyles.smallTextSecondary) {
        Text(loadingMessage)
      }
    }
  }
}
