package learn.algo.dsalgokt.ch03linkedlist.c01reverse

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

sealed class NodeReversibleBaseTest(
    private val nodeReversible: IReversible
) {
    @Test
    fun toStringReversed() {
        assertEquals("1 -> 2 -> 3 -> 4", nodeReversible.toString())
        assertEquals("4 -> 3 -> 2 -> 1", nodeReversible.toStringReversed())
    }
}
