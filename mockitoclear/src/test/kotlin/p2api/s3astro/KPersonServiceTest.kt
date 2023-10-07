package learn.mockito.p2api.s3astro

import learn.mockito.p1foundation.s1person.KPerson
import learn.mockito.p1foundation.s1person.KPersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.internal.verification.VerificationModeFactory.times
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class KPersonServiceTest {
    @Mock
    private lateinit var personRepository: KPersonRepository
    @InjectMocks
    private lateinit var personService: KPersonService

    private val people = listOf(
        KPerson(1, "Grace", "Hopper"),
        KPerson(2, "Ada", "Lovelace"),
        KPerson(3, "Adele", "Goldberg"),
        KPerson(14, "Anita", "Borg"),
        KPerson(5, "Barbara", "Liskov")
    )
    private val allLastNames = arrayOf("Hopper", "Lovelace", "Goldberg", "Borg", "Liskov")

    @Test
    fun getLastNames() {
        `when`(personRepository.findAll()).thenReturn(people)

        val lastNames = personService.getLastNames()

        assertThat(lastNames).contains(*allLastNames)
        verify(personRepository).findAll()
    }

    @Test
    fun findByIds_thenReturnWithMultipleArgs() {
        `when`(personRepository.findById(anyInt()))
            .thenReturn(
                Optional.of(people[0]),
                Optional.of(people[1]),
                Optional.of(people[2]),
                Optional.of(people[3]),
                Optional.of(people[4]),
                Optional.empty()
            )

        val persons = personService.findByIds(1, 2, 3, 14, 5)

        assertThat(persons).containsExactlyElementsOf(people)
        verify(personRepository, times(5)).findById(anyInt())
    }

    @Test
    fun deleteAll_justAsIllustrationOfMockingVoidMethod() {
        `when`(personRepository.findAll())
            .thenReturn(listOf(
                people[0],
                people[3],
            ))
        doNothing().`when`(personRepository).delete(people[0]);
        doThrow(RuntimeException::class.java).`when`(personRepository).delete(people[3]);

        assertThrows<RuntimeException> { personService.deleteAll() }
    }
}
