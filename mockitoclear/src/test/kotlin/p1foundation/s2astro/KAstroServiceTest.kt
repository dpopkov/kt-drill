package learn.mockito.p1foundation.s2astro

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.io.IOException

@ExtendWith(MockitoExtension::class)
class KAstroServiceTest {
    @Mock
    private lateinit var gateway: KGateway<KAstroResponse>
    @InjectMocks
    private lateinit var service: KAstroService

    @Test
    fun getAstroData_usingOwnFakeGateway() {
        val astroService = KAstroService(KFakeGateway())

        val data = astroService.getAstroData()

        for ((craft, number) in data) {
            println("$number astronauts aboard $craft")
            assertAll(
                { assertThat(number).isPositive() },
                { assertThat(craft).isNotBlank() },
            )
        }

    }

    private val mockAstroResponse = KAstroResponse(
        number = 7,
        message = "Success",
        people = listOf(
            KAssignment("name1", "Babylon 5"),
            KAssignment("name2", "Babylon 5"),
            KAssignment("name1", "Nostromo"),
            KAssignment("name1", "USS Cerritos"),
            KAssignment("name2", "USS Cerritos"),
            KAssignment("name3", "USS Cerritos"),
            KAssignment("name4", "USS Cerritos"),
        )
    )

    @Test
    fun getAstroData_usingInjectedMockGateway() {
        `when`(gateway.getResponse()).thenReturn(mockAstroResponse)

        val data = service.getAstroData()

        assertThat(data)
            .containsEntry("Babylon 5", 2L)
            .containsEntry("Nostromo", 1L)
            .containsEntry("USS Cerritos", 4L)
        verify(gateway).getResponse()
    }

    @Test
    fun testAstroData_usingFailedGateway() {
        `when`(gateway.getResponse())
            .thenThrow(RuntimeException(IOException("Network problems")))

        assertThatExceptionOfType(RuntimeException::class.java)
            .isThrownBy { service.getAstroData() }
            .withCauseInstanceOf(IOException::class.java)
            .withMessageContaining("Network problems")
    }
}
