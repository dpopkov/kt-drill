package learn.mockito.p2api.s3astro

import learn.mockito.p1foundation.s1person.KPerson
import learn.mockito.p1foundation.s1person.KPersonRepository
import java.time.LocalDate
import java.util.Optional

class KPersonService(
    private val personRepository: KPersonRepository
) {
    fun getLastNames(): List<String> {
        return personRepository.findAll().mapNotNull(KPerson::last)
    }

    fun findByIds(vararg ids: Int): List<KPerson> {
        return ids.map { id -> personRepository.findById(id) }
            .filter { it.isPresent }
            .map(Optional<KPerson>::get)
    }

    fun deleteAll() {
        personRepository.findAll().forEach { personRepository.delete(it) }
    }

    fun createPerson(id: Int, first: String, last: String, dateOfBirth: String): KPerson {
        val person = KPerson(id, first, last, LocalDate.parse(dateOfBirth))
        return personRepository.save(person)
    }

    fun savePeople(vararg people: KPerson): List<Int> {
        return people.map(personRepository::save).mapNotNull(KPerson::id)
    }
}
