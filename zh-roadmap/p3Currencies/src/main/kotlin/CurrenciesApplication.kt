package learn.zhroadmap.p3currencies

import learn.zhroadmap.p3currencies.currencies.Currencies.*
import learn.zhroadmap.p3currencies.currencies.Currency
import learn.zhroadmap.p3currencies.currencies.CurrencyRepository
import learn.zhroadmap.p3currencies.currencies.StandardCurrencies
import learn.zhroadmap.p3currencies.exchangerates.ExchangeRate
import learn.zhroadmap.p3currencies.exchangerates.ExchangeRateRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.math.BigDecimal

@SpringBootApplication
class CurrenciesApplication(
    private val currencyRepository: CurrencyRepository,
    private val exchangeRateRepository: ExchangeRateRepository,
) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        if (currencyRepository.count() == 0L) {
            val currencies = populateCurrencies()
            if (exchangeRateRepository.count() == 0L) {
                populateExchangeRates(currencies[0], currencies[1])
            }
        }
    }

    private fun populateExchangeRates(currency1: Currency, currency2: Currency ) {
        logger.info("Populating exchange rates...")
        exchangeRateRepository.saveAll(listOf(
            ExchangeRate(BigDecimal(0.01), currency1, currency2),
            ExchangeRate(BigDecimal(100.0), currency2, currency1),
        ))
        logger.info("Stored ${exchangeRateRepository.count()} exchange rates.")
    }

    private fun populateCurrencies(): List<Currency> {
        logger.info("Populating currencies...")
        val currencies = listOf(
            StandardCurrencies[USD],
            StandardCurrencies[RUB],
            StandardCurrencies[EUR],
        )
        currencyRepository.saveAll(currencies)
        logger.info("Stored ${currencyRepository.count()} currencies.")
        return currencies
    }

}

fun main(args: Array<String>) {
    runApplication<CurrenciesApplication>(*args)
}
