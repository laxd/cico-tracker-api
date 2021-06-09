package uk.lawrenceandrews.cico.api.weight

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/weight")
class WeightController(
        private val weightService: WeightService
) {

    @GetMapping("")
    fun getAll(): List<WeightRecording> {
        return weightService.getAll()
    }

    @PostMapping("")
    fun addRecording(
        @RequestBody recording: WeightRecording
    ): String {
        weightService.add(recording)

        return "OK!"
    }

    @GetMapping("/average")
    fun getAverage(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): Double {
        return weightService.getAverage(from, to)
    }

    @GetMapping("/current")
    fun getCurrentWeight(): WeightRecording {
        return weightService.getMostRecent()
    }

    @GetMapping("/change")
    fun getChange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) since: LocalDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): Double {
        return weightService.getChange(since, to)
    }

}