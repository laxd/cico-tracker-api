package uk.lawrenceandrews.cico.api.weight

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class WeightService(
        private val weightRepository: WeightRepository
) {

    fun getAll(): List<WeightRecording> {
        return weightRepository.findAllByOrderByDate()
    }

    fun add(weightRecording: WeightRecording) {
        weightRepository.save(weightRecording)
    }

    fun getAverage(from: LocalDate, to: LocalDate): Double {
        if (from.isAfter(to)) {
            return 0.0
        }

        val results = weightRepository.findByDateBetween(from, to)

        if (results.isEmpty()) {
            return 0.0
        }

        // As between is end exclusive, we need to add 1 to the end date
        val daysBetween = ChronoUnit.DAYS.between(from, to.plusDays(1))

        return results.sumOf { r -> r.weight } / daysBetween
    }

    fun getWeightAt(date: LocalDate) {
        weightRepository.findFirstByDateAfter(date)
    }

    fun getMostRecent(): WeightRecording? {
        return weightRepository.findFirstByOrderByDateDesc()
    }

    fun getChange(from: LocalDate, to: LocalDate): Double {
        val fromWeight = weightRepository.findFirstByDateAfter(from)

        if (fromWeight == null) {
            return 0.0
        }

        val toWeight = weightRepository.findFirstByDateAfter(to)

        if (toWeight == null) {
            // This shouldn't ever happen, as to should be after from
            return 0.0
        }

        return fromWeight.weight - toWeight.weight
    }

}