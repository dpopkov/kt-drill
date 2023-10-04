package learn.mockito.p1foundation

import java.util.*

interface KPersonRepository {
    fun findById(id: Int): Optional<KPerson>
}
