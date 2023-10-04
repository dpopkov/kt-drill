package learn.mockito.p1foundation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class KHelloMockitoTest {
    @Mock
    private lateinit var personRepository: KPersonRepository

    @Mock
    private lateinit var translationService: KTranslationService

    @InjectMocks
    private lateinit var helloMockito: KHelloMockito

    @Test
    fun greetPersonThatExists() {
        `when`(personRepository.findById(anyInt()))
            .thenReturn(Optional.of(KPerson("Grace")))
        `when`(translationService.translate("Hello, Grace, from Mockito!", "en", "en"))
            .thenReturn("Hello, Grace, from Mockito!")

        val greeting = helloMockito.greet(1, "en", "en")
        assertEquals("Hello, Grace, from Mockito!", greeting)

        val inOrder = Mockito.inOrder(personRepository, translationService)
        inOrder.verify(personRepository).findById(anyInt())
        inOrder.verify(translationService).translate("Hello, Grace, from Mockito!", "en", "en")
    }

    @Test
    fun greetPersonThatDoesNotExist() {
        `when`(personRepository.findById(anyInt()))
            .thenReturn(Optional.empty())
        `when`(translationService.translate("Hello, World, from Mockito!", "en", "en"))
            .thenReturn("Hello, World, from Mockito!")

        val greeting = helloMockito.greet(1, "en", "en")
        assertEquals("Hello, World, from Mockito!", greeting)

        val inOrder = Mockito.inOrder(personRepository, translationService)
        inOrder.verify(personRepository).findById(anyInt())
        inOrder.verify(translationService).translate("Hello, World, from Mockito!", "en", "en")
    }
}
