package uk.lawrenceandrews.cico.api.weight

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import uk.lawrenceandrews.cico.api.calories.*
import java.time.LocalDate

internal class RecordingServiceTest {

    @MockK
    private lateinit var recordingRepository: RecordingRepository

    private lateinit var recordingService: RecordingService

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        recordingService = RecordingService(recordingRepository)

    }

    @Test
    fun `getAverage returns 0 if no results in range`() {
        every { recordingRepository.findByDateBetween(any(), any()) }.returns(emptyList())

        val result = recordingService.getStats(LocalDate.now(), LocalDate.now())

        assertEquals(0.0, result.averageCalories)
    }

    @Test
    fun `getAverage returns average value for multiple items in range`() {
        every { recordingRepository.findByDateBetween(any(), any()) }.returns(listOf(
                Recording(LocalDate.of(2021, 1, 1), 2000, CaloriesUnit.KCAL, 100.0, WeightUnit.KGS,),
                Recording(LocalDate.of(2021, 1, 2), 2000, CaloriesUnit.KCAL, 99.0, WeightUnit.KGS,),
                Recording(LocalDate.of(2021, 1, 3), 2000, CaloriesUnit.KCAL, 98.0, WeightUnit.KGS,),
        ))

        val result = recordingService.getStats(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 3))

        assertEquals(99.0, result.averageWeight)
    }

    @Test
    fun `getAverage returns 0 for from after to`() {
        every { recordingRepository.findByDateBetween(any(), any()) }.returns(emptyList())

        val result = recordingService.getStats(LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 3))

        assertEquals(0.0, result.averageWeight)
    }
}