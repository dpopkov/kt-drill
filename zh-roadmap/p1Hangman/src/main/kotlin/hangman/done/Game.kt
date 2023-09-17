package learn.zhroadmap.hangman.done

import learn.zhroadmap.hangman.ICommandInterface

class Game(
    selectedWord: String,
    private val console: ICommandInterface,
) {
    private val word = CheckedWord(selectedWord)
    private val gallows = Gallows()
    private val errors = Errors()
    var finished = false

    val guessedWord: String
        get() {
            if (word.isGuessed) return word.guess
            else throw IllegalStateException("Word is not guessed")
        }

    fun won(): Boolean = when {
        word.isGuessed -> true
        gallows.hangmanIsDead -> false
        else -> throw IllegalStateException("This game is not finished yet")
    }

    fun printState() {
        console.println(gallows)
        console.println("Word: ${word.guess}")
        console.println("Errors: (${errors.count}): ${errors.characters()}")
    }

    fun inputLetter(letter: Char) {
        if (word.check(letter)) {
            if (word.isGuessed) {
                finished = true
            }
        } else {
            errors.add(letter)
            gallows.incrementError()
            if (gallows.hangmanIsDead) {
                finished = true
            }
        }
    }
}
