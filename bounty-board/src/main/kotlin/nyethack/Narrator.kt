package nyethack

import kotlin.random.Random
import kotlin.random.nextInt

var narrationModifier: (String) -> String = { it }

fun changeNarratorMood(name: String? = null) {
    val mood: String
    val modifier: (String) -> String
    when (Random.nextInt(1..8)) {
        1 -> {
            mood = "loud"
            modifier = { message ->
                val numRepeats = 5
                message.uppercase() + "!".repeat(numRepeats)
            }
        }

        2 -> {
            mood = "tired"
            modifier = { message ->
                message.lowercase().replace(" ", "... ")
            }
        }

        3 -> {
            mood = "unsure"
            modifier = { message ->
                "$message?"
            }
        }

        4 -> {
            var narrationGiven = 0
            mood = "like sending an itemized bill"
            modifier = { message ->
                narrationGiven++
                "$message.\n(I have narrated $narrationGiven things)"
            }
        }

        5 -> {
            mood = "lazy"
            modifier = { message ->
                message.take(message.length / 2)
            }
        }

        6 -> {
            mood = "mysterious"
            modifier = {
                it.replace("[LET]".toRegex()) { matched ->
                    when (matched.value) {
                        "L" -> "1"
                        "E" -> "3"
                        "T" -> "7"
                        else -> matched.value
                    }
                }
            }
        }

        7 -> {
            mood = "great"
            modifier = { message ->
                name?.let {
                    when {
                        name.all { it.isUpperCase() } -> message.uppercase()
                        else -> message.lowercase()
                    }
                } ?: message
            }
        }

        else -> {
            mood = "professional"
            modifier = { message ->
                "$message."
            }
        }
    }
    narrationModifier = modifier
    narrate("The narrator begins to feel $mood")
}

inline fun narrate(
    message: String,
    modifier: (String) -> String = { narrationModifier(it) },
) {
    println(modifier(message))
}
