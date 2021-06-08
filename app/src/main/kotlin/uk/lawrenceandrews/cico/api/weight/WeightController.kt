package uk.lawrenceandrews.cico.api.weight

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weight")
class WeightController {

    private val recordings = mutableListOf<WeightRecording>()

    @GetMapping("")
    fun getAll(): List<WeightRecording> {
        return recordings
    }

    @PostMapping("")
    fun addRecording(
        @RequestBody recording: WeightRecording
    ): String {
        recordings.add(recording)

        return "OK!"
    }

}