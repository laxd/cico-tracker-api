package uk.lawrenceandrews.cico.api.calories

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class CaloriesService(
        private val caloriesRepository: CaloriesRepository
) {

    fun getAll(): List<CaloriesRecording> {
        return caloriesRepository.getAll()
    }

    fun add(caloriesRecording: CaloriesRecording) {
        caloriesRepository.addRecording(caloriesRecording)
    }

    fun getAverage(from: LocalDate, to: LocalDate): Long {
        val results = caloriesRepository.getBetween(from, to)

        val daysBetween = ChronoUnit.DAYS.between(from, to)

        return results.sumOf { r -> r.calories } / daysBetween
    }

}