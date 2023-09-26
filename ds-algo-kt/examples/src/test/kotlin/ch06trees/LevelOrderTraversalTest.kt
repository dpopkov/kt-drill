package learn.algo.dsalgokt.ch06trees

class LevelOrderTraversalTest : TraversalBaseTest(
    traversal = LevelOrderTraversal(
        visit = { node -> println(node.value) }
    ),
    expected = """
        1
        2
        3
        4
        5
        6
        7
        8
        9
        10
        11
    """.trimIndent()
)
