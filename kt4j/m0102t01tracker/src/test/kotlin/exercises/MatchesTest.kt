package learn.kt4j.m0102t01tracker.exercises

import learn.kt4j.common.helpers.runUsingSystemInOut
import kotlin.test.Test
import kotlin.test.assertEquals

class MatchesTest {
    @Test
    fun testBobWins() {
        val input = """
            3
            3
            3
            2
        """.trimIndent()
        val game = Game(false, "Alice", "Bob", )
        val expectedOutput = """
            11 left.
            How much 'Alice' should take (1..3)? 8 left.
            How much 'Bob' should take (1..3)? 5 left.
            How much 'Alice' should take (1..3)? 2 left.
            How much 'Bob' should take (1..2)? Player 'Bob' is a winner!
        """.trimIndent()
        val actualOutput = runUsingSystemInOut(input) {
            game.start()
        }.trim()
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testAliceWins() {
        val input = """
            2
            3
            2
            3
            1
        """.trimIndent()
        val game = Game(false, "Alice", "Bob", )
        val expectedOutput = """
            11 left.
            How much 'Alice' should take (1..3)? 9 left.
            How much 'Bob' should take (1..3)? 6 left.
            How much 'Alice' should take (1..3)? 4 left.
            How much 'Bob' should take (1..3)? 1 left.
            How much 'Alice' should take (1..1)? Player 'Alice' is a winner!
        """.trimIndent()
        val actualOutput = runUsingSystemInOut(input) {
            game.start()
        }.trim()
        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun testAliceWinsWithInvalidInput() {
        val input = """
            4
            2
            3
            2
            3
            2
            1
        """.trimIndent()
        val game = Game(false, "Alice", "Bob", )
        val expectedOutput = """
            11 left.
            How much 'Alice' should take (1..3)? You can take 1 to 3. Try again.
            How much 'Alice' should take (1..3)? 9 left.
            How much 'Bob' should take (1..3)? 6 left.
            How much 'Alice' should take (1..3)? 4 left.
            How much 'Bob' should take (1..3)? 1 left.
            How much 'Alice' should take (1..1)? You can take 1. Try again.
            How much 'Alice' should take (1..1)? Player 'Alice' is a winner!
        """.trimIndent()
        val actualOutput = runUsingSystemInOut(input) {
            game.start()
        }.trim()
        assertEquals(expectedOutput, actualOutput)
    }
}
