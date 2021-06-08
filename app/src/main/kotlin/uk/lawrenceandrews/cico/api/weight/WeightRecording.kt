package uk.lawrenceandrews.cico.api.weight

import java.util.Date

class WeightRecording(
        val weight: Double,
        val unit: WeightUnit,
        val timeTaken: Date,
)