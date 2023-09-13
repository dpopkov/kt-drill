package nyethack

fun main() {
    narrate(
        "A hero enters the town of Kronstadt. What is his/her name",
        ::makeTextYellow
    )
    val heroName: String? = readlnOrNull()
    require(!heroName.isNullOrEmpty()) {
        "The hero must have a name"
    }

    changeNarratorMood(heroName)
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")
}

private fun createTitle(s: String): String = when {
    s.all { it.isDigit() } -> "Identifiable"
    s.none { it.isLetter() } -> "The Witness Protection Member"
    s.count { it.lowercase() in "eiaou" } > 4 -> "The Master of Vowels"
    else -> "The Renowned Hero"
}

private fun makeTextYellow(text: String) = "\u001B[33;1m${text}?\u001B[0m"