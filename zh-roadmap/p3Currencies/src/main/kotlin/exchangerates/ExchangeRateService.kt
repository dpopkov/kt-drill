package learn.zhroadmap.p3currencies.exchangerates

import learn.zhroadmap.p3currencies.currencies.CurrencyRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class ExchangeRateService(
    val exchangeRateRepository: ExchangeRateRepository,
    val currencyRepository: CurrencyRepository,
) {
    fun getAllExchangeRates(): List<ExchangeRateDto> {
        return exchangeRateRepository.findAll().map {
            ExchangeRateDto.from(it)
        }
    }

    fun getByCodes(codeOfBase: String, codeOfTarget: String): ExchangeRateDto? {
        val found = exchangeRateRepository.findByBaseCodeAndTargetCode(codeOfBase, codeOfTarget)
        return if (found.isPresent) ExchangeRateDto.from(found.get()) else null
    }

    fun create(dto: ExchangeRateCreateDto): ExchangeRate {
        val base = currencyRepository.findByCode(dto.baseCurrencyCode)
            .orElseThrow { IllegalArgumentException("base currency ${dto.baseCurrencyCode} not found") }
        val target = currencyRepository.findByCode(dto.targetCurrencyCode)
            .orElseThrow { IllegalArgumentException("target currency ${dto.targetCurrencyCode} not found") }
        val exchangeRate = ExchangeRate(base = base, target = target, rate = dto.rate)
        val saved = exchangeRateRepository.save(exchangeRate)
        return saved
    }

    fun updateRate(baseCode: String, targetCode: String, rate: BigDecimal): ExchangeRateDto? {
        val found = exchangeRateRepository.findByBaseCodeAndTargetCode(baseCode, targetCode)
        if (found.isPresent) {
            val exchangeRate = found.get()
            exchangeRate.rate = rate
            exchangeRateRepository.save(exchangeRate)
            return ExchangeRateDto.from(exchangeRate)
        } else {
            return null
        }
    }
}
