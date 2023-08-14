package learn.algo.dsalgokt.examples.ch03linkedlist

import learn.algo.dsalgokt.ch03linkedlist.ILinkedList
import learn.algo.dsalgokt.common.printAndReturn
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class BasicLinkedListTest(
    private val list: ILinkedList<Int>
) {
    @Test
    fun `push - add value at the front of the list`() {
        assertTrue(list.isEmpty())

        list.push(1)
        assertFalse(list.isEmpty())
        printAndReturn(list).also { assertEquals("1", it) }

        list.push(2)
        printAndReturn(list).also { assertEquals("2 -> 1", it) }

        list.push(3).push(4)
        printAndReturn(list).also { assertEquals("4 -> 3 -> 2 -> 1", it) }
    }

    @Test
    fun `append - add value at the end of the list`() {
        assertTrue(list.isEmpty())

        list.append(1)
        assertFalse(list.isEmpty())
        printAndReturn(list).also { assertEquals("1", it) }

        list.append(2)
        printAndReturn(list).also { assertEquals("1 -> 2", it) }

        list.append(3).append(4)
        printAndReturn(list).also { assertEquals("1 -> 2 -> 3 -> 4", it) }
    }

    @Test
    fun `insert - add value after particular node`() {
        assertTrue(list.isEmpty())

        list.push(33).append(11)
        printAndReturn(list).also { assertEquals("33 -> 11", it) }

        list.insert(22, 0)
        printAndReturn(list).also { assertEquals("33 -> 22 -> 11", it) }

        list.insert(0, 2)
        printAndReturn(list).also { assertEquals("33 -> 22 -> 11 -> 0", it) }

        list.append(-11)
        printAndReturn(list).also { assertEquals("33 -> 22 -> 11 -> 0 -> -11", it) }
    }
}
