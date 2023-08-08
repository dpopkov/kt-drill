package learn.kt4j.m0102t01tracker

/**
 * Класс должен уметь: добавлять, заменять, искать по имени, читать все сохраненные данные.
 */
class Tracker {
    private val items: MutableMap<Int, Item> = mutableMapOf()
    private var nextId = 1

    fun add(item: Item): Item {
        item.id = nextId++
        items[item.id!!] = item
        return item
    }

    fun findAll(): List<Item> = items.values.toList()

    fun findById(id: Int): Item? = items[id]

    fun findByName(name: String): List<Item> =
        items.values.filter {
            it.name == name
        }
}
