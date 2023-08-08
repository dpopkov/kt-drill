package learn.kt4j.m0102t01tracker

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class TrackerTest {
    @Test
    fun findById() {
        val tracker = Tracker()
        val bug = Item("Bug")
        val item = tracker.add(bug)
        val result = tracker.findById(item.id!!)
        assertEquals(bug.name, result?.name)
    }

    @Test
    fun fundAll() {
        val tracker = Tracker()
        val first = tracker.add(Item("First"))
        val second = tracker.add(Item("Second"))
        val result = tracker.findAll()
        assertContains(result, first)
        assertContains(result, second)
    }

    @Test
    fun findByNameCheckFirst() {
        val tracker = Tracker()
        tracker.add(Item("First"))
        tracker.add(Item("Second"))
        tracker.add(Item("First"))
        tracker.add(Item("Second"))
        tracker.add(Item("First"))
        val result = tracker.findByName("First")
        assertEquals("First", result[0].name)
        assertEquals(3, result.size)
    }

    @Test
    fun findByNameCheckSecond() {
        val tracker = Tracker()
        tracker.add(Item("First"))
        tracker.add(Item("Second"))
        tracker.add(Item("First"))
        tracker.add(Item("Second"))
        tracker.add(Item("First"))
        val result = tracker.findByName("Second")
        assertEquals("Second", result[0].name)
        assertEquals(2, result.size)
    }
}
