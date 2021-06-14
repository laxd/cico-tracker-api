package uk.lawrenceandrews.cico.api.calories

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
class Recording(
        @Column(unique = true)
        val date: LocalDate,
        var calories: Int? = null,
        var caloriesUnit: CaloriesUnit? = null,
        var weight: Double? = null,
        var weightUnit: WeightUnit? = null,
        @Id @GeneratedValue val id: Int? = null
) {
        @field:CreationTimestamp
        lateinit var createdAt: LocalDateTime
}