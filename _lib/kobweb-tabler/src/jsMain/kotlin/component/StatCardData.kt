package io.toolisticon.kobweb.tabler.component

data class StatCardData(
  val title: String,
  val value: String,
  val note: String,
  val progressPercent: Int? = null,
  val progressClass: String = "bg-primary",
  val badgeText: String? = null,
  val badgeClass: String = "bg-green-lt",
)
