package uk.lawrenceandrews.cico.api.tdee

import org.springframework.stereotype.Service
import uk.lawrenceandrews.cico.api.calories.RecordingService
import java.time.LocalDate

@Service
class TdeeService(
    private val recordingService: RecordingService
) {
    private val caloriesPerKg = 7700

    fun getTdee(): Int {
        // TODO: Calculate TDEE from recordings

        // Get average calories and weight change (over all time? A specific range?)
        val stats = recordingService.getStats(LocalDate.of(2020, 1, 1), LocalDate.now());

        // TODO: Different word than deficit? I.e. for users where target weight is
        // higher than starting weight.

        // From this, calculate the difference in calories (i.e. changeInWeight * caloriesPerKg = calorie difference)

        val calorieDifference = (stats.changeInWeight * caloriesPerKg)/stats.days

        // Add calorie difference to average calories for TDEE
        return (stats.averageCalories + calorieDifference).toInt()
    }

}