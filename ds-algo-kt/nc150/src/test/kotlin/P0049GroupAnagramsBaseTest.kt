package learn.nc150

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

abstract class P0049GroupAnagramsBaseTest(
    private val underTest: P0049GroupAnagrams
) {
    @Test
    fun test() {
        val input = arrayOf("eat","tea","tan","ate","nat","bat")
        val expected = listOf(
            listOf("bat"),
            listOf("nat", "tan"),
            listOf("ate","eat","tea"),
        )
        val actual = underTest.groupAnagrams(input)
        assertListsAreEqual(expected, actual)
    }

    private fun assertListsAreEqual(a: List<List<String>>, b: List<List<String>>) {
        val aSet = a.map { it.toSet() }.toSet()
        val bSet = b.map { it.toSet() }.toSet()
        assertEquals(aSet, bSet)
    }
}