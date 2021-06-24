package uk.lawrenceandrews.cico.api.calories

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class RecordingService(
        private val recordingRepository: RecordingRepository
) {

    fun getAll(): Iterable<Recording> {
        return recordingRepository.findAll()
    }

    fun save(recording: Recording): Recording {
        val existingRecording = recordingRepository.findByDate(recording.date) ?: Recording(recording.date)

        existingRecording.calories = recording.calories ?: existingRecording.calories
        existingRecording.caloriesUnit = recording.caloriesUnit ?: existingRecording.caloriesUnit
        existingRecording.weight = recording.weight ?: existingRecording.weight
        existingRecording.weightUnit = recording.weightUnit ?: existingRecording.weightUnit

        return recordingRepository.save(existingRecording)
    }

    fun getMostRecent(): Recording? {
        return recordingRepository.findFirstByOrderByDateDesc()
    }

    /**
     * Get the statistics for recordings between the two dates.
     *
     * Statistics include average calories, average weight and change in weight (Change in calories
     * consumed in not included, as it is not a useful metric)
     *
     * The average is calculated based on the number of days that have data in them. Days
     * where the recorded calories are `null` are not included in the average. Days where the recorded
     * calories are `0` ARE included in the average.
     */
    fun getStats(from: LocalDate, to: LocalDate): RecordingStatistics {
        val recordingsBetween = recordingRepository.findByDateBetween(from, to)

        val startWeight = recordingsBetween.firstOrNull { r -> r.weight != null }?.weight ?: 0.0
        val endWeight = recordingsBetween.lastOrNull { r -> r.weight != null }?.weight ?: 0.0

        val averageCalories = recordingsBetween.mapNotNull { r -> r.calories }.average()
        val averageWeight = recordingsBetween.mapNotNull { r -> r.weight }.average()

        return RecordingStatistics(
            from,
            to,
            from.until(to, ChronoUnit.DAYS).toInt(),
            if (averageCalories.isNaN()) 0.0 else averageCalories,
            if (averageWeight.isNaN()) 0.0 else averageWeight,
            endWeight - startWeight
        )
    }

    fun getBetween(from: LocalDate, to: LocalDate): List<Recording> {
        return recordingRepository.findByDateBetween(from, to)
    }

}