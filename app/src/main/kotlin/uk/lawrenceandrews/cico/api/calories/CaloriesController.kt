package uk.lawrenceandrews.cico.api.calories

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/calories")
class CaloriesController(
        private val caloriesService: CaloriesService
) {

    @GetMapping("")
    fun getAll(): List<CaloriesRecording> {
        return caloriesService.getAll()
    }

    @PostMapping("")
    fun addRecording(
        @RequestBody recording: CaloriesRecording
    ) {
        caloriesService.add(recording)
    }

    @GetMapping("/average")
    fun getAverage(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): Long {
        return caloriesService.getAverage(from, to)
    }

}