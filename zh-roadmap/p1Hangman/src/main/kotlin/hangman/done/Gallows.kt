package learn.zhroadmap.hangman.done

class Gallows {
    private val numStates = 7
    /*
    0 - начальное состояние
    1..6 - состояние соответствующее кол-ву допущенных ошибок
     */
    private var errorCount = 0
    val hangmanIsDead: Boolean get() = errorCount == (numStates - 1)

    private val images = AsciiImagesLoader(numStates).load()

    fun incrementError() {
        errorCount++
    }

    override fun toString(): String {
        return images[errorCount]
    }
}
