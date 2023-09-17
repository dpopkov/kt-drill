package learn.zhroadmap.hangman

/**
 * Интерфейс для ввода текстовых команд и получения текстового вывода.
 * Введен для отвязывания от стандартных функций типа println() и readln()
 * ради целей тестирования.
 */
interface ICommandInterface {
    fun print(s: Any)
    fun println(s: Any)
    fun readln(): String
}
