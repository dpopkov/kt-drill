package learn.mockito.p1foundation.s1person

import java.time.LocalDate

data class KPerson(
    val id: Int? = null,
    val first: String,
    val last: String? = null,
    val dob: LocalDate? = null,
) {
    companion object {
        val defaultDummy = KPerson(first = "default-dummy")
    }
}
