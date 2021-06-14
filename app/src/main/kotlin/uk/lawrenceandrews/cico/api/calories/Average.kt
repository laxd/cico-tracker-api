package uk.lawrenceandrews.cico.api.calories

import java.time.LocalDate

class Average(
    val from: LocalDate,
    val to: LocalDate,
    val calories: Int,
    val weight: Double
)