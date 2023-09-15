package learn.algo.dsalgokt.ch03linkedlist.c02middle

interface IMiddleFinder {
    /**
     * Найти средний элемент связного списка с головным узлом [head].
     * Например, в списке 1 -> 2 -> 3 будет возвращен 2,
     * а в списке 1 -> 2 -> 3 -> 4 будет возвращен 3.
     */
    fun findMiddle(head: Node): Node
}