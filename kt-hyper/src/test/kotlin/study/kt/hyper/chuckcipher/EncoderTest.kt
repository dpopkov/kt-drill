package study.kt.hyper.chuckcipher

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class EncoderTest {

    @Test
    fun encodeWord() {
        val expected = """
            O = 1001111
            n = 1101110
            e = 1100101
        """.trimIndent() + "\n"
        val actual = Encoder().encodeWord("One")
        assertEquals(expected, actual)
    }
}
