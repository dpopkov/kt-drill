package learn.mockito.p2api.s3astro

import learn.mockito.p1foundation.s1person.KPerson
import learn.mockito.p1foundation.s1person.KPersonRepository
import java.util.*

class KInMemoryPersonRepository : KPersonRepository {
    private val people = mutableListOf<KPerson>()

    override fun findById(id: Int): Optional<KPerson> =
        people.firstOrNull { it.id == id }?.let { Optional.of(it) } ?: Optional.empty()

    override fun save(person: KPerson): KPerson {
        synchronized(people) {
            people.add(person)
        }
        return person
    }

    override fun findAll(): List<KPerson> = people.toList()

    override fun count(): Long = people.size.toLong()

    override fun delete(person: KPerson) {
        synchronized(people) {
            people.remove(person)
        }
    }
}