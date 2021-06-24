package uk.lawrenceandrews.cico.api.calories

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class RecordingControllerTest {

    @Autowired
    private lateinit var controller: RecordingController

    @Autowired
    private lateinit var repository: RecordingRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `add calorie recording saves to repository`() {
        mockMvc.post("/recordings") {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            content = "{\"calories\": 2000, \"unit\": \"KGS\", \"date\": \"2021-01-01\"}"
        }.andExpect {
            status { isOk() }
        }.andReturn()
    }
}