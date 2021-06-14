package uk.lawrenceandrews.cico.api.calories

import org.springframework.stereotype.Service
import java.time.LocalDate

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

    /**
     * Get the average calories recorded between the two dates.
     *
     * The average is calculated based on the number of days that have data in them. Days
     * where the recorded calories are `null` are not included in the average. Days where the recorded
     * calories are `0` ARE included in the average.
     */
    fun getAverage(from: LocalDate, to: LocalDate): Average {
        val results = recordingRepository.findByDateBetween(from, to)

        val averageCalories = results
            .mapNotNull { r -> r.calories }
            .average()

        val averageWeight = results
            .mapNotNull { r -> r.weight }
            .average()

        return Average(from, to, averageCalories.toInt(), averageWeight)
    }

    fun getMostRecent(): Recording? {
        return recordingRepository.findFirstByOrderByDateDesc()
    }

    fun getChange(from: LocalDate, to: LocalDate): Change {
        val fromRecording = recordingRepository.findFirstByDateAfter(from)
        val toRecording = recordingRepository.findFirstByDateAfter(to)

        return Change(
            from,
            to,
            (fromRecording?.calories ?: 0) - (toRecording?.calories ?: 0),
            (fromRecording?.weight ?: 0.0) - (toRecording?.weight ?: 0.0)
        )
    }

}