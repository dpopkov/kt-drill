package learn.zhroadmap.hangman.done

class AsciiImagesLoader(private val numStates: Int = 7) {
    /**
     * Загружает ASCII изображения состояний из файлов ресурсов,
     * где каждое состояние представлено текстовым файлом с именем "gallows{stateNumber}.txt".
     */
    fun load(): Array<String> {
        return Array(numStates) {
            ResourceLoader.readAsText("gallows$it.txt")
        }
    }
}

object ResourceLoader {
    fun readAsText(name: String): String {
        this::class.java.classLoader.getResourceAsStream(name).use {
            return it?.reader()?.readText() ?: throw IllegalArgumentException("Cannot read resource $name")
        }
    }
}
