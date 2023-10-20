package learn.algo.dsalgokt.ch07binarytrees

import learn.algo.dsalgokt.common.IBinaryNode
import kotlin.reflect.KClass

/**
 * Производит сериализацию и десериализацию бинарного дерева.
 * Предполагается, что узлы-листья будут обозначаться в строке через
 * представление их потомков как null.
 * Конкретная последовательность обхода при сериализации или построения дерева
 * при десериализации зависит от конкретной реализации интерфейса.
 */
interface IBinaryTreeSerializer<T> {
    fun serialize(treeNode: IBinaryNode<T>): String

    fun deserialize(input: String, valueDeserializer: (String) -> T): IBinaryNode<T>
}
