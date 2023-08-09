package learn.kt4j.m0102t01tracker

/**
 * Класс должен уметь: добавлять, заменять, искать по имени, читать все сохраненные данные.
 */
class Tracker {
    private val items: MutableMap<Int, Item> = mutableMapOf()
    private var nextId = 1

    fun add(item: Item): Item {
        item.validateName()
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

    fun replace(id: Int, item: Item): Boolean {
        item.validateName()
        return findById(id)?.let { found ->
            items[id] = found.copy(
                name = item.name,
            )
            true
        } ?: false
    }

    fun delete(id: Int) {
        items.remove(id)
    }

    fun Item.validateName() {
        require(name.isNotBlank()) { "Наименование должно быть не пустое" }
    }
}
