package uk.lawrenceandrews.cico.api.weight

import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface WeightRepository : CrudRepository<WeightRecording, Int> {

    fun findAllByOrderByDate(): List<WeightRecording>
    fun findByDateBetween(from: LocalDate, to: LocalDate): List<WeightRecording>
    fun findFirstByDateAfter(date: LocalDate): WeightRecording?
    fun findFirstByOrderByDateDesc(): WeightRecording?

}