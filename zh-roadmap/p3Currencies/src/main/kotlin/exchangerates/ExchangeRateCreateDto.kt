package learn.zhroadmap.p3currencies.exchangerates

import java.math.BigDecimal

data class ExchangeRateCreateDto(
    val rate: BigDecimal,
    val baseCurrencyCode: String,
    val targetCurrencyCode: String
)
