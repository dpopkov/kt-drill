package learn.zhroadmap.hangman.done

import java.io.File

class WordDictionary(
    dictPath: String? = null
) {
    private val words: List<String> =
        dictPath?.let {
            File(it).readLines()
        } ?: ResourceLoader.readAsText("dictionary.txt").lines()

    fun getRandomWord(): String {
        return words.random()
    }
}
