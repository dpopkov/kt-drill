package learn.zhroadmap.p3currencies.exchange

import com.fasterxml.jackson.annotation.JsonInclude
import learn.zhroadmap.p3currencies.currencies.CurrencyDto
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExchangeDto(
    val baseCurrency: CurrencyDto? = null,
    val targetCurrency: CurrencyDto? = null,
    val rate: BigDecimal? = null,
    val amount: BigDecimal? = null,
    val convertedAmount: BigDecimal? = null,
    val message: String? = null,
)
