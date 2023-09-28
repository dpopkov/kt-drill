package learn.zhroadmap.p3currencies.exchangerates

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
class ExchangeRateController(
    private val exchangeRateService: ExchangeRateService
) {
    @GetMapping("/exchangeRates")
    fun getAll(): List<ExchangeRateDto> {
        return exchangeRateService.getAllExchangeRates()
    }

    @GetMapping("/exchangeRates/{codes}")
    fun getByCodes(@PathVariable("codes") codes: String): ResponseEntity<ExchangeRateDto> {
        if (codes.length != 6) return ResponseEntity.badRequest().build()
        val codeOfBase = codes.substring(0, 3)
        val codeOfTarget = codes.substring(3)
        val rate = exchangeRateService.getByCodes(codeOfBase, codeOfTarget)
        return ResponseEntity.ofNullable(rate)
    }

    @PostMapping(value = ["/exchangeRates"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun create(dto: ExchangeRateCreateDto): ResponseEntity<ExchangeRate> {
        val created = exchangeRateService.create(dto)
        return ResponseEntity.ok(created)
    }

    @PatchMapping(value = ["/exchangeRate/{codes}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun updateRate(@PathVariable("codes") codes: String, rate: BigDecimal): ResponseEntity<ExchangeRateDto> {
        if (codes.length != 6) return ResponseEntity.badRequest().build()
        val codeOfBase = codes.substring(0, 3)
        val codeOfTarget = codes.substring(3)
        val updated = exchangeRateService.updateRate(codeOfBase, codeOfTarget, rate)
        return ResponseEntity.ofNullable(updated)
    }
}
