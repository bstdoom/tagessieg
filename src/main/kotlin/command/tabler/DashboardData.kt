package io.github.bstdoom.tagessieg.command.tabler

import kotlinx.serialization.Serializable

@Serializable
data class DashboardData(
    val generatedAt: String,
    val summary: Summary,
    val yearlyResults: List<YearlyResult>,
    val monthlyTrend: MonthlyTrend,
)

@Serializable
data class Summary(
    val jens: Int,
    val holger: Int,
    val grandSlamsJens: Int,
    val grandSlamsHolger: Int,
    val totalDays: Int,
    val latestDay: String,
    val currentLeader: String,
)

@Serializable
data class YearlyResult(
    val year: Int,
    val jens: Int,
    val holger: Int,
    val grandSlams: Int,
    val days: Int,
)

@Serializable
data class MonthlyTrend(
    val labels: List<String>,
    val jens: List<Int>,
    val holger: List<Int>,
)
