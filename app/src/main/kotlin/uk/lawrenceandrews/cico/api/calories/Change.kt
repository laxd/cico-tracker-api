package uk.lawrenceandrews.cico.api.calories

import java.time.LocalDate

class Change(
    val from: LocalDate,
    val to: LocalDate,
    val calories: Int,
    val weight: Double
)