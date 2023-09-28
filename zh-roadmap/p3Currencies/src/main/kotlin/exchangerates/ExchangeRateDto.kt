package learn.zhroadmap.p3currencies.exchangerates

import learn.zhroadmap.p3currencies.currencies.CurrencyDto
import java.math.BigDecimal

data class ExchangeRateDto(
    val rate: BigDecimal,
    val baseCurrency: CurrencyDto,
    val targetCurrency: CurrencyDto,
) {
    companion object {
        fun from(exchangeRate: ExchangeRate): ExchangeRateDto {
            return ExchangeRateDto(
                exchangeRate.rate,
                CurrencyDto.from(exchangeRate.base),
                CurrencyDto.from(exchangeRate.target)
            )
        }
    }

}