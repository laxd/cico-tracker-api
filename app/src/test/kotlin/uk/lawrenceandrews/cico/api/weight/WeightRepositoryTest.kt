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
    fun `getAll returns all items added`() {
        entityManager.persist(WeightRecording(0.0, WeightUnit.KGS, LocalDate.now()))
        entityManager.persist(WeightRecording(0.0, WeightUnit.KGS, LocalDate.now()))
        entityManager.persist(WeightRecording(0.0, WeightUnit.KGS, LocalDate.now()))


        val results = weightRepository.findAllByOrderByDate()

        assertEquals(3, results.size)
    }
}