package learn.mockito.p2api.s3astro

import learn.mockito.p1foundation.s1person.KPerson
import learn.mockito.p1foundation.s1person.KPersonRepository

class KPersonService(
    private val personRepository: KPersonRepository
) {
    fun getLastNames(): List<String> {
        return personRepository.findAll().mapNotNull(KPerson::last)
    }
}
