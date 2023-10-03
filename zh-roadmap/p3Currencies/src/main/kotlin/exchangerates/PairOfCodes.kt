package learn.zhroadmap.p3currencies.exchangerates

@JvmInline
value class PairOfCodes(
    private val codes: String
) {
    init {
        require(codes.length == LENGTH) {
            "codes must be of length ${LENGTH}, but was ${codes.length}"
        }
    }

    val codeOfBase: String get() = codes.substring(0, 3)
    val codeOfTarget: String get() = codes.substring(3)

    companion object {
        const val LENGTH = 6
    }
}
