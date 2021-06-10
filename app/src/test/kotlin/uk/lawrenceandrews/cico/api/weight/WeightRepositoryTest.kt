package uk.lawrenceandrews.cico.api.weight

import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate

@RunWith(SpringRunner::class)
@DataJpaTest
class WeightRepositoryTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var weightRepository: WeightRepository

    @Test
    fun `findAllByOrderByDate returns all items added`() {
        entityManager.persist(WeightRecording(0.0, WeightUnit.KGS, LocalDate.now()))
        entityManager.persist(WeightRecording(0.0, WeightUnit.KGS, LocalDate.now()))
        entityManager.persist(WeightRecording(0.0, WeightUnit.KGS, LocalDate.now()))


        val results = weightRepository.findAllByOrderByDate()

        assertEquals(3, results.size)
    }

    @Test
    fun `findAllByOrderByDate returned all item in chronological order`() {
        entityManager.persist(WeightRecording(1.0, WeightUnit.KGS, LocalDate.of(2021, 1, 1)))
        entityManager.persist(WeightRecording(3.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3)))
        entityManager.persist(WeightRecording(2.0, WeightUnit.KGS, LocalDate.of(2021, 1, 2)))


        val results = weightRepository.findAllByOrderByDate()

        assertEquals(1.0, results[0].weight)
        assertEquals(2.0, results[1].weight)
        assertEquals(3.0, results[2].weight)
    }

    @Test
    fun `findByDateBetween returns only values in range`() {
        entityManager.persist(WeightRecording(1.0, WeightUnit.KGS, LocalDate.of(2020, 12, 1)))
        entityManager.persist(WeightRecording(3.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3)))
        entityManager.persist(WeightRecording(2.0, WeightUnit.KGS, LocalDate.of(2021, 2, 2)))


        val results = weightRepository.findByDateBetween(
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 1, 5)
        )

        assertEquals(1, results.size)
    }

    @Test
    fun `findByDateBetween results inclusive results for both sides of range`() {
        entityManager.persist(WeightRecording(1.0, WeightUnit.KGS, LocalDate.of(2020, 12, 1)))
        entityManager.persist(WeightRecording(3.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3)))
        entityManager.persist(WeightRecording(2.0, WeightUnit.KGS, LocalDate.of(2021, 2, 2)))


        val results = weightRepository.findByDateBetween(
                LocalDate.of(2020, 12, 1),
                LocalDate.of(2021, 2, 2)
        )

        assertEquals(3, results.size)
    }

    @Test
    fun `findFirstByDateAfter returns first result after given date`() {
        entityManager.persist(WeightRecording(1.0, WeightUnit.KGS, LocalDate.of(2020, 12, 1)))
        entityManager.persist(WeightRecording(3.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3)))
        entityManager.persist(WeightRecording(2.0, WeightUnit.KGS, LocalDate.of(2021, 2, 2)))


        val result = weightRepository.findFirstByDateAfter(
                LocalDate.of(2021, 1, 1)
        )

        assertEquals(3.0, result!!.weight)
    }

    @Test
    fun `findFirstByDateAfter results null if no recordings present after date`() {
        entityManager.persist(WeightRecording(1.0, WeightUnit.KGS, LocalDate.of(2020, 12, 1)))
        entityManager.persist(WeightRecording(3.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3)))
        entityManager.persist(WeightRecording(2.0, WeightUnit.KGS, LocalDate.of(2021, 2, 2)))


        val result = weightRepository.findFirstByDateAfter(
                LocalDate.of(2021, 2, 3)
        )

        assertNull(result)
    }

    @Test
    fun `findFirstByOrderByDateDesc returns the last result entered`() {
        entityManager.persist(WeightRecording(1.0, WeightUnit.KGS, LocalDate.of(2020, 12, 1)))
        entityManager.persist(WeightRecording(3.0, WeightUnit.KGS, LocalDate.of(2021, 1, 3)))
        entityManager.persist(WeightRecording(2.0, WeightUnit.KGS, LocalDate.of(2021, 2, 2)))


        val result = weightRepository.findFirstByOrderByDateDesc()

        assertEquals(2.0, result!!.weight)
    }

    @Test
    fun `findFirstByOrderByDateDesc returns null if no results entered`() {
        val result = weightRepository.findFirstByOrderByDateDesc()

        assertNull(result)
    }

}