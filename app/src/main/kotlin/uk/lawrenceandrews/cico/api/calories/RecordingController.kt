package uk.lawrenceandrews.cico.api.calories

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/recordings")
class RecordingController(
        private val recordingService: RecordingService
) {

    @GetMapping("")
    fun getAll(): Map<LocalDate, Recording> {
        return recordingService.getAll().associateBy({ it.date }, { it })
    }

    @GetMapping("between")
    fun getBetween(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): Map<LocalDate, Recording>  {
        return recordingService.getBetween(from, to).associateBy({ it.date }, {it})
    }

    @PostMapping("")
    fun addOrUpdateRecording(
            @RequestBody recording: Recording
    ): Recording {
        return recordingService.save(recording)
    }

    @GetMapping("/average")
    fun getAverages(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): Average {
        return recordingService.getAverage(from, to)
    }

    @GetMapping("/current")
    fun getCurrentWeight(): Recording? {
        return recordingService.getMostRecent()
    }

    @GetMapping("/change")
    fun getChange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?
    ): Change {
        return recordingService.getChange(from, to ?: LocalDate.now())
    }
}