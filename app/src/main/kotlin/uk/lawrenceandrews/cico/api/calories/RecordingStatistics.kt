package uk.lawrenceandrews.cico.api.calories

import java.time.LocalDate

class RecordingStatistics(
    val from: LocalDate,
    val to: LocalDate,
    val days: Int,
    val averageCalories: Double,
    val averageWeight: Double,
    val changeInWeight: Double
)