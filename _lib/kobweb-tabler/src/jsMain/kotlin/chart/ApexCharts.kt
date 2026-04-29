package io.toolisticon.kobweb.tabler.chart

import org.w3c.dom.HTMLElement

external class ApexCharts(element: HTMLElement, options: Any) {
  fun render(): dynamic
  fun destroy(): Unit
}
