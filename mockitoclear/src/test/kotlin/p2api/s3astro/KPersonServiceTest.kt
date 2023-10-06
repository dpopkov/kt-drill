package learn.mockito.p2api.s3astro

import learn.mockito.p1foundation.s1person.KPerson
import learn.mockito.p1foundation.s1person.KPersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

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
}
