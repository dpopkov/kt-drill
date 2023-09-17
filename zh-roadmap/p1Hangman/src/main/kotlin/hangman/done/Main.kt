package learn.zhroadmap.hangman.done

fun main(vararg args: String) {
    val cliArgs = CliArgs(args)
    val dictPath = cliArgs.dictionaryPath()
    dictPath?.let { println("Using dictionary from: $it") }
    GameCycle()
        .start(wordProducer = WordDictionary(dictPath)::getRandomWord)
}
