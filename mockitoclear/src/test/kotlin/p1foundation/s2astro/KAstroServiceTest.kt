package learn.mockito.p1foundation.s2astro

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class KAstroServiceTest {

    private lateinit var astroService: KAstroService

    @Test
    fun getAstroData() {
        astroService = KAstroService(KFakeGateway())

        val data = astroService.getAstroData()

        for ((craft, number) in data) {
            println("$number astronauts aboard $craft")
            assertAll(
                { assertThat(number).isPositive() },
                { assertThat(craft).isNotBlank() },
            )
        }

    }
}
