package nyethack

import java.io.File

private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"

private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastNames = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

private val menuData: List<String> = File("data/tavern-menu-data.txt").readLines()
private val menuItems: List<String> = List(menuData.size) { idx ->
    val (type, name, price) = menuData[idx].split(",")
    name
}

fun visitTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    println("There are several items for sale:")
    printMenu(menuItems, menuData)

    val patrons: MutableSet<String> = mutableSetOf()
    while(patrons.size < 10) {
        patrons += "${firstNames.random()} ${lastNames.random()}"
    }

    narrate("$heroName sees several patrons in the tavern:")
    narrate(patrons.joinToString())

    repeat(3) {
        placeOrder(patrons.random(), menuItems.random())
    }
}

fun printMenu(items: List<String>, data: List<String>) {
    val width = items.maxBy { it.length }.length + 9
    println("*** Welcome to $TAVERN_NAME ***")
    val byType: MutableMap<String, MutableList<String>> = mutableMapOf()
    data.forEach { line ->
        val (type, name, price) = line.split(",")
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

private fun placeOrder(patronName: String, menuItemName: String) {
    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    narrate("$TAVERN_MASTER hands $patronName a $menuItemName")
}
