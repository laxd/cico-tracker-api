package uk.lawrenceandrews.cico.api.weight

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import java.time.LocalDate

internal class WeightServiceTest {

    @MockK
    private lateinit var weightRepository: WeightRepository

    private lateinit var weightService: WeightService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        weightService = WeightService(weightRepository)

    }

    @Test
    fun `getAverage returns 0 if no results in range`() {
        every { weightRepository.findByDateBetween(any(), any()) }.returns(emptyList())

        val result = weightService.getAverage(LocalDate.now(), LocalDate.now())

        assertEquals(0.0, result)
    }

    @Test
    fun `getAverage returns average value for multiple items in range`() {
        every { weightRepository.findByDateBetween(any(), any()) }.returns(listOf(
                WeightRecording(100.0, WeightUnit.KGS, LocalDate.of(2021, 1, 1)),
                WeightRecording(99.0, WeightUnit.KGS, LocalDate.of(2021, 1, 2)),
                WeightRecording(98.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3))
        ))

        val result = weightService.getAverage(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 1, 3))

        assertEquals(99.0, result)
    }

    @Test
    fun `getAverage returns 0 for from after to`() {
        val result = weightService.getAverage(LocalDate.of(2021, 1, 5), LocalDate.of(2021, 1, 3))

        assertEquals(0.0, result)
    }
}