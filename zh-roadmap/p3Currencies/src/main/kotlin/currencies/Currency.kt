package learn.zhroadmap.p3currencies.currencies

import jakarta.persistence.*

@Entity
data class Currency(
    val name: String,
    @Column(unique = true)
    val code: String,
    val sign: String,
    @Id
    @GeneratedValue
    var id: Long? = null,
)
