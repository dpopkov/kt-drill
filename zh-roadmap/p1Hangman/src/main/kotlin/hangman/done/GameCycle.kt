package learn.zhroadmap.hangman.done

import learn.zhroadmap.hangman.ICommandInterface
import learn.zhroadmap.hangman.IGameCycle

class GameCycle : IGameCycle {
    override var console: ICommandInterface = SystemCLI()

    override fun start(wordProducer: () -> String) {
        var startNewGame: Boolean
        do {
            startNewGame = readCommand()
            if (startNewGame) {
                val game = Game(wordProducer.invoke(), console)
                while (!game.finished) {
                    game.printState()
                    game.inputLetter(readLetter())
                    if (game.finished) {
                        if (game.won()) {
                            console.println("You guessed the word ${game.guessedWord}!")
                        } else {
                            game.printState()
                            console.println("You lost the game!")
                        }
                    }
                }
            }
        } while (startNewGame)
    }

    private fun readCommand(): Boolean {
        var result: Boolean?
        do {
            console.print("[N]ew game or e[X]it? ")
            val command = console.readln().lowercase()
            result = when (command) {
                "n", "new", "new game" -> true
                "x", "exit", "q", "quit" -> false
                else -> {
                    console.println("Invalid input: \"${command}\". Try again.")
                    null
                }
            }
        } while (result == null)
        return result
    }

    private fun readLetter(): Char {
        var tryingToRead = true
        var letter: Char? = null
        do {
            console.print("Letter: ")
            val s = console.readln()
            val result = AsciiLetterValidator.valid(s)
            if (result.valid) {
                tryingToRead = false
                letter = s[0]
            } else {
                console.println("${result.message}. Try again.")
            }
        } while (tryingToRead)
        return letter ?: throw IllegalStateException("Failed to get letter")
    }

}

/**
 * Реализация ввода-вывода для интерфейса командной строки
 * с использованием System.out.
 * Выполнена отдельным классом, чтобы отвязаться от функций Kotlin
 * println() и readln(), и облегчить тестирование игрового цикла.
 */
@Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
private class SystemCLI : ICommandInterface {
    override fun print(s: Any) {
        System.out.print(s)
    }

    override fun println(s: Any) {
        System.out.println(s)
    }

    override fun readln(): String {
        return readlnOrNull() ?: ""
    }
}
