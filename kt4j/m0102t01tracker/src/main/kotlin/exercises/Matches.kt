package learn.kt4j.m0102t01tracker.exercises

import kotlin.math.min

/*
Необходимо сделать игру 11. Смысл игры в следующем. На столе лежат 11 спичек.
Два игрока по очереди берут от 1 до 3 спичек. Выигрывает тот, кто забрал последние спички.
 */

fun main(args: Array<String>) {
    val firstIsRandom = args.firstOrNull { it.startsWith("--firstIsRandom=") }
        ?.let { it.substringAfter('=').toBoolean() } ?: false
    val game = Game(firstIsRandom, "Alice", "Bob")
    game.start()
}

class Game(firstIsRandom: Boolean, vararg names: String) {
    private val players = Players(firstIsRandom, names)

    fun start() {
        val matches = Matches()
        while (!matches.isOut()) {
            players.next().also {
                println("${matches.count} left.")
                val maxAmount = min(3, matches.count)
                val amount = it.input(maxAmount)
                matches.take(it, amount)
            }
        }
    }
}

fun Player.input(maxAmount: Int): Int {
    do {
        print("How much '$name' should take (1..$maxAmount)? ")
        val amount = readln().toInt()
        if (amount in 1..maxAmount) {
            return amount
        }
        val msg = if (maxAmount == 1) "You can take 1. Try again."
        else "You can take 1 to $maxAmount. Try again."
        println(msg)
    } while (true)
}

interface Player {
    val name: String
}

class Players(firstIsRandom: Boolean = true, names: Array<out String>) {
    private val list: List<IdPlayer>
    private var nextPlayer: IdPlayer

    init {
        var nextId = 1
        list = names.map {
            IdPlayer(nextId++, it)
        }
        nextPlayer = if (firstIsRandom) list.random() else list[0]
    }

    fun next(): Player {
        val currentNext = nextPlayer
        val nextId = if (currentNext.id == list.size) 1 else currentNext.id + 1
        nextPlayer = list[nextId - 1]
        return currentNext
    }

    private class IdPlayer(val id: Int, override val name: String) : Player
}

class Matches(initialCount: Int = 11) {
    var count = initialCount
        private set

    fun isOut(): Boolean = count == 0

    fun take(player: Player, amount: Int) {
        check(amount <= count) {
            "You cannot take more than $count matches"
        }
        count -= amount
        if (isOut()) {
            println("Player '${player.name}' is a winner!")
        }
    }
}
