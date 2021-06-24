package uk.lawrenceandrews.cico.api.tdee

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.lawrenceandrews.cico.api.calories.*
import java.time.LocalDate
import kotlin.test.assertEquals

internal class TdeeServiceTest {

    private lateinit var tdeeService: TdeeService

    @MockK
    private lateinit var recordingService: RecordingService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        tdeeService = TdeeService(recordingService)
    }

    @Test
    internal fun `TDEE calculation returns average calories if no change in weight`() {
        val r = RecordingStatistics(
            LocalDate.now(),
            LocalDate.now(),
            14,
            2000.0,
            100.0,
            0.0
        )

        every { recordingService.getStats(any(), any()) }.returns(r)

        val tdee = tdeeService.getTdee()

        assertEquals(2000, tdee)
    }


    @Test
    internal fun `TDEE calculation returns 1000 above average for loss over 10 days`() {
        val r = RecordingStatistics(
            LocalDate.now(),
            LocalDate.now(),
            10,
            2000.0,
            100.0,
            1.299
        )

        every { recordingService.getStats(any(), any()) }.returns(r)

        val tdee = tdeeService.getTdee()

        assertEquals(3000, tdee)
    }


}