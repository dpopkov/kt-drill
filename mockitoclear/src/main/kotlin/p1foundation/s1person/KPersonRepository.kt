package learn.mockito.p1foundation.s1person

import java.util.*

interface KPersonRepository {
    fun findById(id: Int): Optional<KPerson>

    fun save(person: KPerson): KPerson

    fun findAll(): List<KPerson>

    fun count(): Long

    fun delete(person: KPerson)
}
