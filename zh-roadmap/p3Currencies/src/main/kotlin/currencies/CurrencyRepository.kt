package learn.zhroadmap.p3currencies.currencies

import org.springframework.data.repository.CrudRepository
import java.util.*

interface CurrencyRepository : CrudRepository<Currency, Long> {
    fun findByCode(code: String): Optional<Currency>
}
