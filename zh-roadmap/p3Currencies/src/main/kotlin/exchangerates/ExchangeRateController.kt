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
    fun getByCodes(@PathVariable("codes") codes: PairOfCodes): ResponseEntity<ExchangeRateDto> {
        val rate = exchangeRateService.getByCodes(codes.codeOfBase, codes.codeOfTarget)
        return ResponseEntity.ofNullable(rate)
    }

    @PostMapping(value = ["/exchangeRates"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun create(dto: ExchangeRateCreateDto): ResponseEntity<ExchangeRate> {
        val created = exchangeRateService.create(dto)
        return ResponseEntity.ok(created)
    }

    @PatchMapping(value = ["/exchangeRate/{codes}"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun updateRate(@PathVariable("codes") codes: PairOfCodes, rate: BigDecimal): ResponseEntity<ExchangeRateDto> {
        val updated = exchangeRateService.updateRate(codes.codeOfBase, codes.codeOfTarget, rate)
        return ResponseEntity.ofNullable(updated)
    }
}
