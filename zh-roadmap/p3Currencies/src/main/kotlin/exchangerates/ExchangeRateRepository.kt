package learn.zhroadmap.p3currencies.exchangerates

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ExchangeRateRepository : CrudRepository<ExchangeRate, Long> {
    fun findByBaseCodeAndTargetCode(baseCode: String, targetCode: String): Optional<ExchangeRate>
}
