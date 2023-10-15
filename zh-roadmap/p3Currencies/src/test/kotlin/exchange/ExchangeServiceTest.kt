package learn.zhroadmap.p3currencies.exchange

import learn.zhroadmap.p3currencies.currencies.Currencies.*
import learn.zhroadmap.p3currencies.currencies.StandardCurrencies
import learn.zhroadmap.p3currencies.exchangerates.ExchangeRate
import learn.zhroadmap.p3currencies.exchangerates.ExchangeRateRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@ExtendWith(MockitoExtension::class)
class ExchangeServiceTest {
    @Mock
    lateinit var exchangeRateRepository: ExchangeRateRepository

    @InjectMocks
    lateinit var service: ExchangeService

    private val usdToRubRate40 = BigDecimal("40.0")
    private val usdToRub = ExchangeRate(
        rate = usdToRubRate40,
        base = StandardCurrencies[USD],
        target = StandardCurrencies[RUB],
    )

    private fun BigDecimal.divideRate(divider: BigDecimal) =
        this.divide(divider, 3, RoundingMode.HALF_UP)

    private val rubToUsd = ExchangeRate(
        rate = BigDecimal.ONE.divideRate(usdToRubRate40),
        base = StandardCurrencies[RUB],
        target = StandardCurrencies[USD],
    )
    private val usdToEur = ExchangeRate(
        rate = BigDecimal("2.0"),
        base = StandardCurrencies[USD],
        target = StandardCurrencies[EUR],
    )

    @Test
    fun `convert with existing rate`() {
        exchangeRateExpectation("USD", "RUB", Optional.of(usdToRub))

        val result = service.convert("USD", "RUB", BigDecimal.TEN)

        assertEquals("USD", result.baseCurrency?.code)
        assertEquals("RUB", result.targetCurrency?.code)
        assertEquals(usdToRubRate40, result.rate)
        assertEquals(BigDecimal.TEN, result.amount)
        assertEquals(BigDecimal("400.0"), result.convertedAmount)
        assertNull(result.message)
        verify(exchangeRateRepository).findByBaseCodeAndTargetCode(anyString(), anyString())
    }

    @Test
    fun `convert with reversed rate`() {
        exchangeRateExpectation("USD", "RUB", Optional.empty())
        exchangeRateExpectation("RUB", "USD", Optional.of(rubToUsd))

        val result = service.convert("USD", "RUB", BigDecimal.TEN)

        assertEquals("USD", result.baseCurrency?.code)
        assertEquals("RUB", result.targetCurrency?.code)
        assertTrue(result.rate.equalsByValue(BigDecimal("40")))
        assertEquals(BigDecimal.TEN, result.amount)
        assertTrue(result.convertedAmount.equalsByValue(BigDecimal("400")))
        assertNull(result.message)
        verify(exchangeRateRepository, times(2)).findByBaseCodeAndTargetCode(anyString(), anyString())
    }

    @Test
    fun `convert using existing pairs of currencies`() {
        exchangeRateExpectation("EUR", "RUB", Optional.empty())
        exchangeRateExpectation("RUB", "EUR", Optional.empty())
        exchangeRateExpectation("USD", "RUB", Optional.of(usdToRub))
        exchangeRateExpectation("USD", "EUR", Optional.of(usdToEur))

        val result = service.convert("EUR", "RUB", BigDecimal.TEN)

        assertEquals("EUR", result.baseCurrency?.code)
        assertEquals("RUB", result.targetCurrency?.code)
        assertTrue(result.rate.equalsByValue(BigDecimal("20")))
        assertEquals(BigDecimal.TEN, result.amount)
        assertTrue(result.convertedAmount.equalsByValue(BigDecimal("200")))
        assertNull(result.message)
        verify(exchangeRateRepository, times(4)).findByBaseCodeAndTargetCode(anyString(), anyString())
    }

    @Test
    fun `convert of non existing currencies returns error message`() {
        exchangeRateExpectation("EUR", "RUB", Optional.empty())
        exchangeRateExpectation("RUB", "EUR", Optional.empty())
        exchangeRateExpectation("USD", "RUB", Optional.empty())
        exchangeRateExpectation("USD", "EUR", Optional.empty())

        val result = service.convert("EUR", "RUB", BigDecimal.TEN)

        assertEquals("Currencies not found", result.message)
        assertNull(result.baseCurrency)
        assertNull(result.targetCurrency)
        assertNull(result.rate)
        assertNull(result.amount)
        assertNull(result.convertedAmount)
        verify(exchangeRateRepository, times(4)).findByBaseCodeAndTargetCode(anyString(), anyString())
    }

    private fun exchangeRateExpectation(base: String, target: String, expectedResult: Optional<ExchangeRate>) {
        `when`(exchangeRateRepository.findByBaseCodeAndTargetCode(base, target)).thenReturn(expectedResult)
    }

    /**
     * Сравнивает только значение, не учитывая scale.
     * Поэтому "20.00" будет равно "20", тогда как для equals они не равны.
     */
    private fun BigDecimal?.equalsByValue(other: BigDecimal) =
        this != null && this.compareTo(other) == 0
}
