package learn.kt4j.m0102t01tracker

import kotlin.test.*

class TrackerTest {

    @Test
    fun addWithEmptyName() {
        val tracker = Tracker()
        assertFailsWith(IllegalArgumentException::class) {
            tracker.add(Item(name = ""))
        }
    }

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

    @Test
    fun replaceSuccessful() {
        val tracker = Tracker()
        val bug = Item("Bug")
        val item = tracker.add(bug)
        val id = item.id!!
        val success: Boolean = tracker.replace(id, Item("Bug with description"))
        assertTrue(success)
        assertEquals("Bug with description", tracker.findById(id)?.name)
    }

    @Test
    fun replaceWithEmptyName() {
        val tracker = Tracker()
        assertFailsWith(IllegalArgumentException::class) {
            tracker.replace(0, Item(name = ""))
        }
    }

    @Test
    fun replaceUnsuccessful() {
        val tracker = Tracker()
        val bug = Item("Bug")
        val item = tracker.add(bug)
        val success: Boolean = tracker.replace(1000, Item("Bug with description"))
        assertFalse(success)
        assertEquals("Bug", tracker.findById(item.id!!)?.name)
    }

    @Test
    fun deleteSuccessful() {
        val tracker = Tracker()
        val bug = Item("Bug")
        val item = tracker.add(bug)
        val id = item.id!!
        tracker.delete(id)
        assertNull(tracker.findById(id))
    }

    @Test
    fun deleteUnsuccessful() {
        val tracker = Tracker()
        val bug = Item("Bug")
        val item = tracker.add(bug)
        val id = item.id!!
        tracker.delete(1000)
        assertEquals("Bug", tracker.findById(id)?.name)
    }
}
