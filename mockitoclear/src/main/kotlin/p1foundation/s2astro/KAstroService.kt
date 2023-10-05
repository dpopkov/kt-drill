package learn.mockito.p1foundation.s2astro

class KAstroService(
    private val gateway: KGateway<KAstroResponse>
) {
    fun getAstroData(): Map<String, Long> {
        val data = gateway.getResponse()
        return data.people
            .groupBy { it.craft }
            .mapValues { it.value.size.toLong() }
    }
}
