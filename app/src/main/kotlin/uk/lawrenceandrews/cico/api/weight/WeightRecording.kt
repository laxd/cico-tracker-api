package uk.lawrenceandrews.cico.api.weight

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class WeightRecording(
        val weight: Double,
        val unit: WeightUnit,
        val date: LocalDate,
        @Id @GeneratedValue val id: Int? = null
)