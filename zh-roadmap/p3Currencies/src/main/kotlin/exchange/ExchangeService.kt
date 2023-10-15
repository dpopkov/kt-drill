package learn.zhroadmap.p3currencies.exchange

import learn.zhroadmap.p3currencies.currencies.CurrencyDto
import learn.zhroadmap.p3currencies.exchangerates.ExchangeRateRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode

@Service
class ExchangeService(
    private val exchangeRateRepository: ExchangeRateRepository,
) {
    fun convert(from: String, to: String, amount: BigDecimal): ExchangeDto {
        val found = exchangeRateRepository.findByBaseCodeAndTargetCode(from, to)
        if (found.isPresent) {
            val exchangeRate = found.get()
            val convertedAmount = amount.multiply(exchangeRate.rate)
            return ExchangeDto(
                baseCurrency = CurrencyDto.from(exchangeRate.base),
                targetCurrency = CurrencyDto.from(exchangeRate.target),
                rate = exchangeRate.rate,
                amount = amount,
                convertedAmount = convertedAmount,
            )
        }

        val foundReversed = exchangeRateRepository.findByBaseCodeAndTargetCode(to, from)
        if (foundReversed.isPresent) {
            val reversedRate = foundReversed.get()
            val calculatedRate = BigDecimal.ONE.divide(reversedRate.rate)
            val convertedAmount = amount.multiply(calculatedRate)
            return ExchangeDto(
                baseCurrency = CurrencyDto.from(reversedRate.target),
                targetCurrency = CurrencyDto.from(reversedRate.base),
                rate = calculatedRate,
                amount = amount,
                convertedAmount = convertedAmount,
            )
        }

        val foundUsdToBase = exchangeRateRepository.findByBaseCodeAndTargetCode("USD", from)
        val foundUsdToTarget = exchangeRateRepository.findByBaseCodeAndTargetCode("USD", to)
        if (foundUsdToBase.isPresent && foundUsdToTarget.isPresent) {
            val usdToBase = foundUsdToBase.get()
            val usdToTarget = foundUsdToTarget.get()
            // Calculating: base --> usd --> target
            val calculatedRate = usdToTarget.rate.divide(usdToBase.rate, 3, RoundingMode.HALF_UP)
            val convertedAmount = amount.multiply(calculatedRate)
            return ExchangeDto(
                baseCurrency = CurrencyDto.from(usdToBase.target),
                targetCurrency = CurrencyDto.from(usdToTarget.target),
                rate = calculatedRate,
                amount = amount,
                convertedAmount = convertedAmount,
            )
        }

        return ExchangeDto(
            message = "Currencies not found"
        )
    }
}
