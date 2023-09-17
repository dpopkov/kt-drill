package learn.zhroadmap.hangman.done

class CliArgs(private val arguments: Array<out String>) {
    private val dictPrefix = "--dict="

    fun dictionaryPath(): String? = arguments.find {
        it.startsWith(dictPrefix)
    }?.substring(dictPrefix.length)
}
