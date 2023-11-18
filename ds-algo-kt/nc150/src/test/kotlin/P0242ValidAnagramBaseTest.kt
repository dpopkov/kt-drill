package learn.nc150

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

abstract class P0242ValidAnagramBaseTest(
    private val sut: P0242ValidAnagram
) {
    @Test
    fun test() {
        assertTrue(sut.isAnagram("anagram", "nagaram"))
        assertFalse(sut.isAnagram("rat", "car"))
    }
}
