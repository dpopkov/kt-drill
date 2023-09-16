package nyethack

import java.io.File
import kotlin.random.Random
import kotlin.random.nextInt

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData: List<List<String>> = File("data/tavern-menu-data.txt")
    .readLines()
    .map { it.split(",") }
private val menuItems: List<String> = menuData.map { (_, name, _) -> name }
private val menuItemPrices: Map<String, Double> = menuData.associate { (_, name, price) ->
    name to price.toDouble()
}
private val menuItemTypes: Map<String, String> = menuData.associate { (type, name, _) ->
    name to type
}

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    println("There are several items for sale:")
    printMenu(menuItems, menuData)

    val patrons: MutableSet<String> = firstNames.shuffled()
        .zip(lastNames.shuffled()) { firstName, lastName -> "$firstName $lastName" }
        .toMutableSet()
    val patronGold = mutableMapOf(
        TAVERN_MASTER to 86.00,
        heroName to 4.50,
        *patrons.map { it to 6.0 }.toTypedArray(),
    )

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    val itemOfDay = patrons.flatMap { getFavoriteMenuItems(it) }.random()
    println("The item of the day is $itemOfDay")

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random(), patronGold, menuItemPrices, menuItemTypes)
    }
    displayPatronBalances(patronGold)

    patrons
        .filter { patron ->
            patronGold.getOrDefault(patron, 0.0) < 4.0
        }.also {
            patrons -= it
            patronGold -= it
        }.forEach { patron ->
            narrate("$heroName sees $patron departing the tavern")
        }

    narrate("There are still some patrons in the tavern")
    narrate(patrons.joinToString())
}

fun printMenu(items: List<String>, data: List<List<String>>) {
    val width = items.maxBy { it.length }.length + 9
    println("*** Welcome to $TAVERN_NAME ***")
    val byType: MutableMap<String, MutableList<String>> = mutableMapOf()
    data.forEach { line ->
        val (type, name, price) = line
        byType.getOrPut(type) { mutableListOf() }
            .add(format(name, price, width))
    }
    byType.forEach { (t, list) ->
        println(formatCentered("~[$t]~", width))
        list.forEach { println(it) }
    }
}

private fun formatCentered(s: String, width: Int) = s.padStart((width - s.length) / 2 + s.length)

private fun format(name: String, price: String, width: Int): String {
    val dots = ".".repeat(width - name.length - price.length)
    return "$name$dots$price"
}

private fun masterSaysAbout(allPatrons: List<String>, vararg somePatrons: String) {
    var joinedNames = somePatrons.takeLast(2).joinToString(" and ")
    if (somePatrons.size > 2) {
        joinedNames = somePatrons.take(somePatrons.size - 2).joinToString(", ") + ", " + joinedNames
    }
    val othersMessage = if (allPatrons.containsAll(somePatrons.toList())) {
        "$TAVERN_MASTER says: $joinedNames are seated by the stew kettle"
    } else {
        "$TAVERN_MASTER says: $joinedNames aren't with with each other right now"
    }
    println(othersMessage)
}

private fun getFavoriteMenuItems(patron: String): List<String> {
    return when (patron) {
        "Alex Ironfoot" -> menuItems.filter { menuItem ->
            menuItemTypes[menuItem]?.contains("dessert") == true
        }
        else -> menuItems.shuffled().take(Random.nextInt(1..2))
    }
}

/**
 * Сообщает о заказе клиента.
 */
private fun placeOrder(
    patronName: String,
    menuItemName: String,
    patronGold: MutableMap<String, Double>,
    menuItemPrices: Map<String, Double>,
    menuItemTypes: Map<String, String>,
) {
    val itemPrice = menuItemPrices.getValue(menuItemName)
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        val action = when (menuItemTypes[menuItemName]) {
            "shandy", "elixir" -> "pours"
            "meal" -> "serves"
            else -> "hands"
        }
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_MASTER $itemPrice gold")
        patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
    }
}

private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach { (patron, balance) ->
        narrate("$patron has ${"%.2f".format(balance)} gold")
    }
}