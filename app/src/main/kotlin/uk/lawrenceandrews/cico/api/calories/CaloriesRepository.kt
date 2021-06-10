package uk.lawrenceandrews.cico.api.calories

import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class CaloriesRepository {

    // TODO: Tmp storage, replace with DB
    private val recordings = mutableListOf<CaloriesRecording>()

    fun getAll(): List<CaloriesRecording> {
        return recordings
    }

    fun getBetween(from: LocalDate, to: LocalDate): List<CaloriesRecording> {
        return recordings.filter { r -> !(r.date.isBefore(from) || r.date.isAfter(to)) }
    }

    fun addRecording(caloriesRecording: CaloriesRecording) {
        recordings.add(caloriesRecording)
    }


}