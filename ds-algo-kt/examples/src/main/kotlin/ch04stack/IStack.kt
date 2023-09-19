package learn.algo.dsalgokt.ch04stack

interface IStack<Element> {
    val size: Int

    fun isEmpty(): Boolean

    fun push(element: Element)

    fun pop(): Element

    fun peek(): Element?
}
