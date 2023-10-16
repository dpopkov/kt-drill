package learn.zhroadmap.p3currencies.currencies

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/currencies")
class CurrencyController(
    private val currencyService: CurrencyService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<CurrencyDto>> {
        return ResponseEntity.ok(currencyService.getAll())
    }

    @GetMapping("/{code}")
    fun getByCode(@PathVariable("code") code: String): ResponseEntity<CurrencyDto> {
        val currency = currencyService.getByCode(code)
        return ResponseEntity.ofNullable(currency)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun create(currency: CurrencyDto): ResponseEntity<CurrencyDto> {
        return ResponseEntity.ok(currencyService.create(currency))
    }
}
