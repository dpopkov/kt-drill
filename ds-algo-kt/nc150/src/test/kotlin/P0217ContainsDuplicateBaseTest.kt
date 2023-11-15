package learn.nc150

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

abstract class P0217ContainsDuplicateBaseTest(
    private val sut: P0217ContainsDuplicate
) {
    @Test
    fun test() {
        assertTrue(sut.containsDuplicate(intArrayOf(1, 2, 3, 1)))
        assertFalse(sut.containsDuplicate(intArrayOf(1, 2, 3, 4)))
        assertTrue(sut.containsDuplicate(intArrayOf(1, 1, 1, 3, 3, 4, 3, 2, 4, 2)))
    }
}
