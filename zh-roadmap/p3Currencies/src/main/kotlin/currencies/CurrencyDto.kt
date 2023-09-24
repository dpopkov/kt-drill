package learn.zhroadmap.p3currencies.currencies

data class CurrencyDto(
    val name: String,
    val code: String,
    val sign: String,
) {
    fun toEntity(): Currency {
        return Currency(name, code, sign)
    }

    companion object {
        fun from(currency: Currency): CurrencyDto {
            return CurrencyDto(currency.name, currency.code, currency.sign)
        }
    }
}
