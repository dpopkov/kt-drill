package learn.zhroadmap.p3currencies.currencies

import org.springframework.stereotype.Service

@Service
class CurrencyService(
    private val currencyRepository: CurrencyRepository
) {
    fun getAll(): List<CurrencyDto> {
        return currencyRepository.findAll().map { CurrencyDto.from(it) }
    }

    fun getByCode(code: String): CurrencyDto? {
        val found = currencyRepository.findByCode(code)
        return found.map { CurrencyDto.from(it) }.orElse(null)
    }

    fun create(currency: CurrencyDto): CurrencyDto {
        return CurrencyDto.from(currencyRepository.save(currency.toEntity()))
    }

}
