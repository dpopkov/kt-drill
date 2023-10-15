package learn.zhroadmap.p3currencies.exchange

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
class ExchangeController(
    private val exchangeService: ExchangeService,
) {
    @GetMapping("/exchange")
    fun exchange(
        @RequestParam from: String,
        @RequestParam to: String,
        @RequestParam amount: BigDecimal
    ): ResponseEntity<ExchangeDto> {
        val dto = exchangeService.convert(from, to, amount)
        return if (dto.message == null) {
            ResponseEntity.ok(dto)
        } else {
            ResponseEntity(dto, HttpStatus.BAD_REQUEST)
        }
    }
}
