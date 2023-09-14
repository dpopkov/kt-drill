package learn.algo.dsalgokt.ch03linkedlist.c01reverse

interface IReversible {
    /**
     * Сформировать перевернутое строковое представление списка.
     * Например toString() возвращает "1 -> 2 -> 3",
     * тогда toStringReversed() возвращает "3 -> 2 -> 1".
     */
    fun toStringReversed(): String
}