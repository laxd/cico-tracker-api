package uk.lawrenceandrews.cico.api.weight

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import uk.lawrenceandrews.cico.api.calories.Recording
import uk.lawrenceandrews.cico.api.calories.RecordingRepository
import uk.lawrenceandrews.cico.api.calories.WeightUnit
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@DataJpaTest
class RecordingRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var recordingRepository: RecordingRepository

    @Test
    fun `findByDateBetween returns all items added`() {
        entityManager.persist(Recording(LocalDate.of(2021, 1, 1),null, null, 1.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 3),null, null, 3.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 2),null, null, 2.0, WeightUnit.KGS))

        val results = recordingRepository.findByDateBetween(
            LocalDate.of(1, 1, 1),
            LocalDate.of(9999, 1, 1)
        )

        assertEquals(3, results.size)
    }

    @Test
    fun `findByDateBetween returned all item in chronological order`() {
        entityManager.persist(Recording(LocalDate.of(2021, 1, 1),null, null, 1.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 3),null, null, 3.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 2),null, null, 2.0, WeightUnit.KGS))

        val results = recordingRepository.findByDateBetween(
            LocalDate.of(2021, 1, 1),
            LocalDate.of(2021, 1, 3)
        )

        assertEquals(1.0, results[0].weight)
        assertEquals(2.0, results[1].weight)
        assertEquals(3.0, results[2].weight)
    }

    @Test
    fun `findByDateBetween returns only values in range`() {
        entityManager.persist(Recording(LocalDate.of(2021, 1, 1),null, null, 1.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 5),null, null, 3.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 2),null, null, 2.0, WeightUnit.KGS))


        val results = recordingRepository.findByDateBetween(
                LocalDate.of(2021, 1, 2),
                LocalDate.of(2021, 1, 3)
        )

        assertEquals(1, results.size)
    }

    @Test
    fun `findByDateBetween results inclusive results for both sides of range`() {
        entityManager.persist(Recording(LocalDate.of(2021, 1, 1),null, null, 1.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 3),null, null, 3.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 2),null, null, 2.0, WeightUnit.KGS))

        val results = recordingRepository.findByDateBetween(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2021, 1, 3)
        )

        assertEquals(3, results.size)
    }

    @Test
    fun `findFirstByOrderByDateDesc returns the last result entered`() {
        entityManager.persist(Recording(LocalDate.of(2021, 1, 1),null, null, 1.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 3),null, null, 3.0, WeightUnit.KGS))
        entityManager.persist(Recording(LocalDate.of(2021, 1, 2),null, null, 2.0, WeightUnit.KGS))

        val result = recordingRepository.findFirstByOrderByDateDesc()

        assertEquals(3.0, result!!.weight)
    }

    @Test
    fun `findFirstByOrderByDateDesc returns null if no results entered`() {
        val result = recordingRepository.findFirstByOrderByDateDesc()

        assertNull(result)
    }

}