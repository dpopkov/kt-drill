package learn.zhroadmap.p3currencies.currencies

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
        return if (currency != null) {
            ResponseEntity.ok(currency)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody currency: CurrencyDto): ResponseEntity<CurrencyDto> {
        return ResponseEntity.ok(currencyService.create(currency))
    }
}
