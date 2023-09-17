package learn.zhroadmap.hangman

import learn.zhroadmap.hangman.done.AsciiImagesLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private val stateImages: Array<String> = AsciiImagesLoader(numStates = 7).load()

open class GameCycleBaseTest(
    val gameCycle: IGameCycle
) {
    private class TestCli(
        private val validSingleCycle: Boolean = true,
    ) : ICommandInterface {
        val buffer = StringBuilder()
        private var inputs = listOf("exit")
        private var inputIdx = 0

        fun initCommands(vararg commands: String): TestCli {
            inputs = buildList {
                if (validSingleCycle) add("new")
                addAll(commands)
                if (validSingleCycle) add("exit")
            }
            return this
        }

        override fun print(s: Any) {
            buffer.append(s)
        }

        override fun println(s: Any) {
            buffer.append(s)
            buffer.append("\n")
        }

        override fun readln(): String = inputs[inputIdx++]
    }

    private val testingWordProducer: () -> String = { "hangman" }

    @Test
    fun testSuccess() {
        val expectedOutput = """[N]ew game or e[X]it? ${stateImages[0]}
Word: _______
Errors: (0): 
Letter: ${stateImages[0]}
Word: h______
Errors: (0): 
Letter: ${stateImages[0]}
Word: ha___a_
Errors: (0): 
Letter: ${stateImages[0]}
Word: han__an
Errors: (0): 
Letter: ${stateImages[0]}
Word: hang_an
Errors: (0): 
Letter: You guessed the word hangman!
[N]ew game or e[X]it? """
        val cli = TestCli().initCommands("h", "a", "n", "g", "m")
        gameCycle.apply {
            console = cli
        }.start(testingWordProducer)
        assertEquals(expectedOutput, cli.buffer.toString())
    }

    @Test
    fun testFailure() {
        val expectedOutput = """[N]ew game or e[X]it? ${stateImages[0]}
Word: _______
Errors: (0): 
Letter: ${stateImages[1]}
Word: _______
Errors: (1): b
Letter: ${stateImages[2]}
Word: _______
Errors: (2): b, c
Letter: ${stateImages[3]}
Word: _______
Errors: (3): b, c, d
Letter: ${stateImages[4]}
Word: _______
Errors: (4): b, c, d, e
Letter: ${stateImages[5]}
Word: _______
Errors: (5): b, c, d, e, f
Letter: ${stateImages[6]}
Word: _______
Errors: (6): b, c, d, e, f, i
You lost the game!
[N]ew game or e[X]it? """
        val cli = TestCli().initCommands("b", "c", "d", "e", "f", "i")
        gameCycle.apply {
            console = cli
        }.start(testingWordProducer)
        assertEquals(expectedOutput, cli.buffer.toString())
    }

    @Test
    fun testInvalidCommand() {
        val cli = TestCli(validSingleCycle = false).initCommands("invalid-start", "exit")
        val expectedOutput = """[N]ew game or e[X]it? Invalid input: "invalid-start". Try again.
[N]ew game or e[X]it? """
        gameCycle.apply {
            console = cli
        }.start(testingWordProducer)

        assertEquals(expectedOutput, cli.buffer.toString())
    }
}
