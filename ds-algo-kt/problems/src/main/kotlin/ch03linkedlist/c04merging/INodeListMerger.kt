package learn.algo.dsalgokt.ch03linkedlist.c04merging

interface INodeListMerger {
    /**
     * Производит слияние связных списков [head1] и [head2].
     * Предполагается, что оба списка отсортированы по возрастанию.
     * Возвращает новый список, составленный из узлов исходных списков.
     */
    fun mergeSorted(head1: Node, head2: Node): Node
}
