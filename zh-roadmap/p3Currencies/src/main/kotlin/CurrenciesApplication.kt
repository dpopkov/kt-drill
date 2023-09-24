package learn.zhroadmap.p3currencies

import learn.zhroadmap.p3currencies.currencies.Currency
import learn.zhroadmap.p3currencies.currencies.CurrencyRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CurrenciesApplication(
    private val currencyRepository: CurrencyRepository,
) : CommandLineRunner {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        if (currencyRepository.count() == 0L) {
            populateCurrencies()
        }
    }

    private fun populateCurrencies() {
        logger.info("Populating currencies...")
        currencyRepository.saveAll(
            listOf(
                Currency("United States Dollar", "USD", "$"),
//                Currency("Euro", "EUR", "€"),
                Currency("Ruble", "RUB", "₽"),
            )
        )
        logger.info("Stored ${currencyRepository.count()} currencies.")
    }

}

fun main(args: Array<String>) {
    runApplication<CurrenciesApplication>(*args)
}
