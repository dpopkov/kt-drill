package study.kt.hyper.chuckcipher

class EncoderBeginner {
    fun encode(character: Char): String {
        var result = ""
        var charAsInt = character.code
        while (charAsInt != 0) {
            val digit = charAsInt % 2
            result = digit.toString() + result
            charAsInt /= 2
        }
        while (result.length < 7) {
            result = "0$result"
        }
        return result
    }

    fun encodeWord(word: String): String {
        var result = ""
        for (ch in word) {
            result += "$ch = ${encode(ch)}\n"
        }
        return result
    }
}
