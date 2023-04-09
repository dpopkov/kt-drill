package study.kt.hyper.chuckcipher

class Encoder {

    fun encodeWord(word: String): String {
        val builder = StringBuilder()
        word.forEach { ch ->
            val binary = Integer.toBinaryString(ch.code).padStart(7, '0')
            builder.append("$ch = $binary\n")
        }
        return builder.toString()
    }
}

fun main() {
    println("Input string:")
    val input = readln()
    val result = Encoder().encodeWord(input)
    print(result)
}
