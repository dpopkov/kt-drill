package learn.zhroadmap.p3currencies.currencies

enum class Currencies {
    USD,
    RUB,
    EUR
}

object StandardCurrencies {
    private val map: MutableMap<Currencies, Currency> = mutableMapOf()

    init {
        map[Currencies.USD] = Currency("United States Dollar", "USD", "$")
        map[Currencies.RUB] = Currency("Ruble", "RUB", "₽")
        map[Currencies.EUR] = Currency("Euro", "EUR", "€")
    }

    operator fun get(currency: Currencies) =
        map[currency] ?: throw IllegalArgumentException("Unknown currency $currency")
}
