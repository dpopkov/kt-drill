package learn.mockito.p2api.s3astro

import learn.mockito.p1foundation.s1person.KPerson
import learn.mockito.p1foundation.s1person.KPersonRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.internal.verification.VerificationModeFactory.times
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class KPersonServiceTest {
    @Mock
    private lateinit var personRepository: KPersonRepository
    @InjectMocks
    private lateinit var personService: KPersonService
    @Captor
    private lateinit var personCaptor: ArgumentCaptor<KPerson>

    private val people = listOf(
        KPerson(1, "Grace", "Hopper", LocalDate.parse("1906-12-09")),
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
        doNothing().`when`(personRepository).delete(people[0])
        doThrow(RuntimeException::class.java).`when`(personRepository).delete(people[3])

        assertThrows<RuntimeException> { personService.deleteAll() }
    }

    @Test
    fun findByIdThatDoesNotExist_usingCustomMatcher() {
        // val greaterThan14 = { id: Int -> id > 14 }
        // `when`(personRepository.findById(intThat(greaterThan14)))
        `when`(personRepository.findById(intThat { it > 14 }))
            .thenReturn(Optional.empty())

        val persons = personService.findByIds(15, 42, 78, 123)

        assertTrue(persons.isEmpty())
        verify(personRepository, times(4)).findById(anyInt())
    }

    /**
     * Необходим для обхода проблемы с non-nullable параметрами Kotlin.
     * Нужно использовать в случае, если результат capture передается в метод,
     * принимающий аргумент non-nullable типа.
     */
    private fun <T> ArgumentCaptor<T>.captureK(): T = this.capture()

    @Test
    fun `create Person using Date String`() {
        val hopper = people[0]
        `when`(personRepository.save(hopper)).thenReturn(hopper)

        val actual = personService.createPerson(1, "Grace", "Hopper", "1906-12-09")

        verify(personRepository).save(personCaptor.captureK())
        assertThat(personCaptor.value).isEqualTo(hopper)
        assertThat(actual).isEqualTo(hopper)
    }

    /**
     * Применяется, чтобы уйти от платформенного типа <code>KPerson!</code>
     * и избежать ошибки с приходом nullable значения.
     */
    private fun anyPerson(): KPerson = any(KPerson::class.java) ?: KPerson.defaultDummy

    @Test
    fun `saveAllPeople - using thenReturn`() {
        `when`(personRepository.save(anyPerson()))
            .thenReturn(
                people[0],
                people[1],
                people[2],
                people[3],
                people[4],
            )

        val actualIds = personService.savePeople(*people.toTypedArray<KPerson>())

        val expectedIds = people.map(KPerson::id)
        assertEquals(expectedIds, actualIds)
        verify(personRepository, times(people.size)).save(anyPerson())
        verify(personRepository, never()).delete(anyPerson())
    }

    @Test
    fun `saveAllPeople - using thenAnswer`() {
        `when`(personRepository.save(anyPerson()))
            .thenAnswer { it.getArgument<KPerson>(0) }

        val actualIds = personService.savePeople(*people.toTypedArray<KPerson>())

        val expectedIds = people.map(KPerson::id)
        assertEquals(expectedIds, actualIds)
        verify(personRepository, times(people.size)).save(anyPerson())
        verify(personRepository, never()).delete(anyPerson())
    }

    @Test
    fun `testInMemoryPersonRepository without using Spy`() {
        val repo: KPersonRepository = KInMemoryPersonRepository()
        val service = KPersonService(repo)

        service.savePeople(*people.toTypedArray())

        assertThat(repo.findAll()).isEqualTo(people)
    }

    @Test
    fun `testInMemoryPersonRepository using Spy`() {
        val inMemoryRepo = KInMemoryPersonRepository()
        val repo: KPersonRepository = spy(inMemoryRepo)
        val service = KPersonService(repo)

        service.savePeople(*people.toTypedArray())

        assertThat(repo.findAll()).isEqualTo(people)
        assertThat(inMemoryRepo.findAll()).isEqualTo(people)
        verify(repo, times(people.size)).save(anyPerson())
    }

    @Test
    fun testMockOfFinal() {
        // В Kotlin классы по умолчанию final
        val repo = mock(KInMemoryPersonRepository::class.java)
        `when`(repo.save(anyPerson()))
            .thenAnswer { it.getArgument<KPerson>(0) }
        val service = KPersonService(repo)

        val ids = service.savePeople(*people.toTypedArray())

        assertThat(ids).containsExactly(1, 2, 3, 14, 5)
        verify(repo, times(5)).save(anyPerson())
    }
}
