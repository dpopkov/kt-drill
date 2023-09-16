package learn.algo.dsalgokt.ch03linkedlist.c03reverselist

interface INodeListReversible {
    /**
     * Переворачивает связный список, начинающийся с данного узла.
     * Возвращает голову перевернутого списка
     */
    fun reverse(): Node
}
