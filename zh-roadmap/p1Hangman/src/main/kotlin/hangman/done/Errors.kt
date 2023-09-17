package learn.zhroadmap.hangman.done

class Errors {
    private val errorChars = mutableListOf<Char>()

    /**
     * Суммарное количество ошибок.
     */
    val count: Int get() = errorChars.size

    /**
     * Все символы, которые привели к ошибкам.
     */
    fun characters() = errorChars.joinToString()

    /**
     * Добавить символ приведший к ошибке.
     */
    fun add(ch: Char) {
        errorChars.add(ch)
    }
}
