package io.toolisticon.kobweb.tabler.asset

import kotlinx.browser.document
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.suspendCancellableCoroutine
import org.w3c.dom.HTMLLinkElement
import org.w3c.dom.HTMLScriptElement
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TablerCssId = "tabler-css"
private const val TablerCssHref = "https://cdn.jsdelivr.net/npm/@tabler/core@1.4.0/dist/css/tabler.min.css"

private const val ApexChartsScriptId = "apexcharts-js"
private const val ApexChartsSrc = "https://cdn.jsdelivr.net/npm/apexcharts/dist/apexcharts.min.js"

private var apexChartsLoad = CompletableDeferred<Unit>()

fun ensureTablerCssLoaded() {
  if (document.getElementById(TablerCssId) != null) return

  val link = document.createElement("link") as HTMLLinkElement
  link.id = TablerCssId
  link.rel = "stylesheet"
  link.href = TablerCssHref
  document.head?.appendChild(link)
}

suspend fun ensureApexChartsLoaded() {
  val windowApexCharts = js("window.ApexCharts")
  if (windowApexCharts != null && windowApexCharts != js("undefined")) return

  if (!apexChartsLoad.isCompleted && document.getElementById(ApexChartsScriptId) == null) {
    loadScriptOnce()
  }

  apexChartsLoad.await()
}

private suspend fun loadScriptOnce() {
  suspendCancellableCoroutine<Unit> { continuation ->
    val script = document.createElement("script") as HTMLScriptElement
    script.id = ApexChartsScriptId
    script.src = ApexChartsSrc
    script.async = true
    script.defer = true
    script.addEventListener("load", { _: dynamic ->
      if (!apexChartsLoad.isCompleted) {
        apexChartsLoad.complete(Unit)
      }
      continuation.resume(Unit)
    })
    script.addEventListener("error", { _: dynamic ->
      val error = IllegalStateException("Failed to load ApexCharts from $ApexChartsSrc")
      if (!apexChartsLoad.isCompleted) {
        apexChartsLoad.completeExceptionally(error)
      }
      continuation.resumeWithException(error)
    })
    document.head?.appendChild(script)
  }
}
