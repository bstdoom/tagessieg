package io.github.bstdoom.tagessieg.site.components

data class DashboardData(
  val generatedAt: String,
  val summary: Summary,
  val yearlyResults: List<YearlyResult>,
  val monthlyTrend: MonthlyTrend,
)
