package uk.lawrenceandrews.cico.api.tdee

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.lawrenceandrews.cico.api.calories.RecordingService
import java.time.LocalDate

@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/tdee")
class TdeeController(
    private val tdeeService: TdeeService
) {
    @GetMapping("/current")
    fun getCurrentTdee(): Int {
        return tdeeService.getTdee()
    }

}