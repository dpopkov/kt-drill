import java.lang.IllegalArgumentException

const val HERO_NAME: String = "Madrigal"
var playerLevel: Int = 0

fun main(args: Array<String>) {
    println("$HERO_NAME announces her presence to the world.")
    println("What level is $HERO_NAME?")
    playerLevel = readln().toIntOrNull() ?: 1
    println("$HERO_NAME's level is $playerLevel")

    readBountyBoard()

    println("Time passes...")
    println("$HERO_NAME returns from her quest.")

    playerLevel += 1
    println(playerLevel)
    readBountyBoard()
}

private fun readBountyBoard() {
    val message: String =
        try {
            obtainQuest(playerLevel)?.replace("[Nn]ogartse".toRegex(), "xxxxxxxx")
                ?.let { censoredQuest ->
                    """
                    $HERO_NAME approaches the bounty board. It reads:
                        "$censoredQuest"
                    """.trimIndent()
                } ?: "$HERO_NAME approaches the bounty board, but it is blank"
        } catch (ex: Exception) {
            "$HERO_NAME can't read what's on the bounty board"
        }
    println(message)
}

private fun obtainQuest(
    playerLevel: Int,
    playerClass: String = "paladin",
    hasBefriendedBarbarians: Boolean = true,
    hasAngeredBarbarians: Boolean = false
): String? {
    if (playerLevel < 1) {
        throw InvalidPlayerLevelException()
    }
    return when (playerLevel) {
        1 -> "Meet Mr.Bubble in the land of soft things."

        in 2..5 -> {
            // Проверить возможность дипломатического решения
            val canTalkToBarbarians =
                !hasAngeredBarbarians && (hasBefriendedBarbarians || playerClass == "barbarian")
            if (canTalkToBarbarians) {
                "Convince the barbarians to call off their invasion."
            } else {
                "Save the town from the barbarian invasions."
            }
        }

        6 -> "Locate the enchanted sword."
        7 -> "Recover the long-lost artifact of creation."
        8 -> "Defeat Nogartse, bringer of death and eater of worlds."
        else -> null
    }
}

internal class InvalidPlayerLevelException() :
        IllegalArgumentException("Invalid player level (must be at least 1)")
