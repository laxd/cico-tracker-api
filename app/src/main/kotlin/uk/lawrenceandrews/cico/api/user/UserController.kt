package uk.lawrenceandrews.cico.api.user

import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate


@CrossOrigin(origins = ["*"])
@RestController
@RequestMapping("/user")
class UserController {

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Int): User {
        return User(
            "Test",
            90.0,
            LocalDate.now(),
            id
        )
    }

}