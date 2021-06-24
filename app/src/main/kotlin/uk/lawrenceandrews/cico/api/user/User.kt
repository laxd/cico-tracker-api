package uk.lawrenceandrews.cico.api.user

import java.time.LocalDate
import javax.persistence.GeneratedValue
import javax.persistence.Id

class User(
    val name: String,
    val goalWeight: Double,
    val goalDate: LocalDate,
    @Id @GeneratedValue val id: Int? = null
)