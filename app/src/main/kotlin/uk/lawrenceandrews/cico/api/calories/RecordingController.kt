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
    fun getAll(): Iterable<Recording> {
        return recordingService.getAll()
    }

    @GetMapping("between")
    fun getBetween(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate
    ): Iterable<Recording>  {
        return recordingService.getBetween(from, to)
    }

    @PostMapping("")
    fun addOrUpdateRecording(
            @RequestBody recording: Recording
    ): Recording {
        return recordingService.save(recording)
    }

    @GetMapping("/current")
    fun getCurrentWeight(): Recording? {
        return recordingService.getMostRecent()
    }

    @GetMapping("/stats")
    fun getChange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: LocalDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate?
    ): RecordingStatistics {
        return recordingService.getStats(from, to ?: LocalDate.now())
    }
}