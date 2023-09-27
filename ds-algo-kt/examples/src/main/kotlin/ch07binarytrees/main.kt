package learn.algo.dsalgokt.ch07binarytrees

fun main() {
    val n0 = BinaryNode(0)
    val n1 = BinaryNode(1)
    val n5 = BinaryNode(5)
    val n7 = BinaryNode(7)
    val n8 = BinaryNode(8)
    val n9 = BinaryNode(9)

    n1.left = n0
    n1.right = n5
    n9.left = n8
    n7.left = n1
    n7.right = n9

    print(n7)
}
