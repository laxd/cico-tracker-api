package uk.lawrenceandrews.cico.api.calories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import uk.lawrenceandrews.cico.api.weight.WeightRecording
import java.time.LocalDate

@Repository
interface RecordingRepository: CrudRepository<Recording, Int> {

    fun findByDate(date: LocalDate): Recording?
    fun findFirstByDateAfter(date: LocalDate): Recording?
    fun findByDateBetween(from: LocalDate, to: LocalDate): List<Recording>
    fun findFirstByOrderByDateDesc(): Recording?
}