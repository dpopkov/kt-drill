package learn.zhroadmap.p3currencies.exchangerates

import jakarta.persistence.*
import learn.zhroadmap.p3currencies.currencies.Currency
import java.math.BigDecimal

@Entity
data class ExchangeRate(
    var rate: BigDecimal,
    @ManyToOne(fetch = FetchType.EAGER)
    val base: Currency,
    @ManyToOne(fetch = FetchType.EAGER)
    val target: Currency,
    @Id
    @GeneratedValue
    var id: Long? = null,
)
