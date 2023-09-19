package learn.algo.dsalgokt.ch04stack

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class StackTest {

    @Test
    fun testPushPop() {
        val stack = Stack<Int>()
        stack.push(11)
        stack.push(22)

        assertFalse(stack.isEmpty())
        assertEquals(22, stack.peek())

        val expected = """
            -top-
            22
            11
            -----
        """.trimIndent()
        assertEquals(expected, stack.toString())

        assertEquals(22, stack.pop())
        assertEquals(11, stack.pop())
        assertTrue(stack.isEmpty())
        assertNull(stack.peek())
    }

    @Test
    fun testCreate() {
        val items = listOf(11, 22, 33)
        val stack = Stack.create(items)
        val expected = """
            -top-
            33
            22
            11
            -----
        """.trimIndent()
        assertEquals(expected, stack.toString())
    }
}
