package study.kt.hyper.chuckcipher

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EncoderBeginnerTest {

    @Test
    fun encode() {
        val one = "One"
        assertEquals("1001111", EncoderBeginner().encode(one[0]))
        assertEquals("1101110", EncoderBeginner().encode(one[1]))
        assertEquals("1100101", EncoderBeginner().encode(one[2]))
    }

    @Test
    fun encodeWord() {
        val one = "One"
        val expected = """
            O = 1001111
            n = 1101110
            e = 1100101
        """.trimIndent() + "\n"
        val actual = EncoderBeginner().encodeWord(one)
        assertEquals(expected, actual)
    }
}
