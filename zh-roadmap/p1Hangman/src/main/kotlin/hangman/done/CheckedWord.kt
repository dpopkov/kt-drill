package learn.zhroadmap.hangman.done

class CheckedWord(
    private val hiddenWord: String,
    maskingChar: Char = '_',
) {
    private val _guessed = CharArray(hiddenWord.length) { maskingChar }
    val guess: String get() = _guessed.concatToString()
    val isGuessed: Boolean get() = hiddenWord == guess

    fun check(ch: Char): Boolean {

        var start = 0
        val indicesOfFound = buildList {
            var foundAt = hiddenWord.indexOf(ch, start)
            while (foundAt != -1) {
                add(foundAt)
                start = foundAt + 1
                foundAt = hiddenWord.indexOf(ch, start)
            }
        }
        if (indicesOfFound.isNotEmpty()) {
            for (i in indicesOfFound) {
                _guessed[i] = hiddenWord[i]
            }
            return true
        } else {
            return false
        }
    }
}
