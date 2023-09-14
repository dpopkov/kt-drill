package nyethack

var heroName: String = ""

fun main() {
    heroName = promptHeroName()

//    changeNarratorMood(heroName)
    narrate("$heroName, ${createTitle(heroName)}, heads to the town square")
    visitTavern()
}

private fun promptHeroName(): String {
    narrate(
        message = "A hero enters the town of Kronstadt. What is his/her name",
        modifier = ::makeTextYellow
    )
    println("Madrigal")
    return "Madrigal"
}

private fun createTitle(s: String): String = when {
    s.all { it.isDigit() } -> "Identifiable"
    s.none { it.isLetter() } -> "The Witness Protection Member"
    s.count { it.lowercase() in "eiaou" } > 4 -> "The Master of Vowels"
    else -> "The Renowned Hero"
}

private fun makeTextYellow(text: String) = "\u001B[33;1m${text}?\u001B[0m"