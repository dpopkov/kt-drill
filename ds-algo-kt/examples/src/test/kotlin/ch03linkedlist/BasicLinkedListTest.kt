package learn.algo.dsalgokt.examples.ch03linkedlist

import learn.algo.dsalgokt.ch03linkedlist.ILinkedList
import learn.algo.dsalgokt.common.printAndReturn
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `pop - remove value at the front`() {
        list.push(1).push(2).push(3)

        val v1 = list.pop()
        assertEquals(3, v1)
        printAndReturn(list).also { assertEquals("2 -> 1", it) }

        val v2 = list.pop()
        assertEquals(2, v2)
        printAndReturn(list).also { assertEquals("1", it) }

        val v3 = list.pop()
        assertEquals(1, v3)
        assertTrue(list.isEmpty())

        val v4 = list.pop()
        assertNull(v4)
    }

    @Test
    fun `removeLast - remove value at the end`() {
        list.push(1).push(2).push(3)

        val v1 = list.removeLast()
        assertEquals(1, v1)
        printAndReturn(list).also { assertEquals("3 -> 2", it) }

        val v2 = list.removeLast()
        assertEquals(2, v2)
        printAndReturn(list).also { assertEquals("3", it) }

        val v3 = list.removeLast()
        assertEquals(3, v3)
        assertTrue(list.isEmpty())

        val v4 = list.removeLast()
        assertNull(v4)
    }

    @Test
    fun `removeAfter - remove value after index`() {
        list.push(1).push(2).push(3)

        val v1 = list.removeAfter(0)
        assertEquals(2, v1)
        printAndReturn(list).also { assertEquals("3 -> 1", it) }

        val v2 = list.removeAfter(0)
        assertEquals(1, v2)
        printAndReturn(list).also { assertEquals("3", it) }

        val v3 = list.removeAfter(0)
        assertNull(v3)
        assertFalse(list.isEmpty())
    }

    @Test
    fun `iterator - returns iterator object`() {
        list.push(1).push(2).push(3)

        val i = list.iterator()
        assertTrue(i.hasNext())
        assertEquals(3, i.next())
        assertTrue(i.hasNext())
        assertEquals(2, i.next())
        assertTrue(i.hasNext())
        assertEquals(1, i.next())
        assertFalse(i.hasNext())
    }

    @Test
    fun `iterator removes single element`() {
        list.push(3)
        val i = list.iterator()
        assertTrue(i.hasNext())
        assertEquals(3, i.next())
        i.remove()
        assertFalse(i.hasNext())
        assertTrue(list.isEmpty())
    }

    @Test
    fun `iterator - returned iterator removes first element`() {
        list.push(1).push(2).push(3)

        val i = list.iterator()
        assertTrue(i.hasNext())
        assertEquals(3, i.next())
        i.remove()
        assertTrue(i.hasNext())

        val i2 = list.iterator()
        assertEquals(2, i2.next())
        assertTrue(i2.hasNext())
        assertEquals(1, i2.next())
        assertFalse(i2.hasNext())
        assertEquals(2, list.size)
    }

    @Test
    fun `iterator - returned iterator removes middle element`() {
        list.push(1).push(2).push(3)

        val i = list.iterator()
        assertEquals(3, i.next())
        assertEquals(2, i.next())
        i.remove()
        assertTrue(i.hasNext())

        val i2 = list.iterator()
        assertEquals(3, i2.next())
        assertTrue(i2.hasNext())
        assertEquals(1, i2.next())
        assertFalse(i2.hasNext())
        assertEquals(2, list.size)
    }

    @Test
    fun `iterator - returned iterator removes last element`() {
        list.push(1).push(2).push(3)

        val i = list.iterator()
        assertEquals(3, i.next())
        assertEquals(2, i.next())
        assertEquals(1, i.next())
        i.remove()
        assertFalse(i.hasNext())

        val i2 = list.iterator()
        assertEquals(3, i2.next())
        assertTrue(i2.hasNext())
        assertEquals(2, i2.next())
        assertFalse(i2.hasNext())
        assertEquals(2, list.size)

        // Test appending
        list.append(11)
        printAndReturn(list).also { assertEquals("3 -> 2 -> 11", it) }
    }

    @Test
    fun `iterator - returned iterator removes after remove`() {
        list.push(1).push(2).push(3).push(4)

        val i = list.iterator()
        assertEquals(4, i.next())
        assertEquals(3, i.next())
        i.remove()
        printAndReturn(list).also { assertEquals("4 -> 2 -> 1", it) }
        assertEquals(2, i.next())
        i.remove()
        printAndReturn(list).also { assertEquals("4 -> 1", it) }
    }

    @Test
    fun `iterator - throws exception if next was not called`() {
        list.push(1)

        assertThrows<IllegalStateException> {
            list.iterator().remove()
        }
    }

    @Test
    fun `iterator - throws exception if next was not called after remove`() {
        list.push(1).push(2)
        val i = list.iterator()
        assertEquals(2, i.next())
        i.remove()

        assertThrows<IllegalStateException> {
            i.remove()
        }
    }

    @Test
    fun `iterator - throws exception if there's no nodes`() {
        assertThrows<IllegalStateException> {
            list.iterator().remove()
        }
    }

    @Test
    fun `contains - checks if element is contained`() {
        list.push(1).push(2)

        assertTrue(list.contains(1))
        assertTrue(list.contains(2))
        assertFalse(list.contains(3))
    }

    @Test
    fun `containsAll - checks if all elements are contained`() {
        list.push(1).push(2)

        assertTrue(list.containsAll(listOf(1, 2)))
        assertFalse(list.containsAll(listOf(1, 2, 3)))
    }
}
