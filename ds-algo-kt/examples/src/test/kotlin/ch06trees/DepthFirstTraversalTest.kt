package learn.algo.dsalgokt.ch06trees

class DepthFirstTraversalTest : TraversalBaseTest(
    traversal = DepthFirstTraversal(
        visit = { node -> println(node.value) }
    ),
    expected = """
        1
        2
        4
        8
        9
        5
        3
        6
        7
        10
        11
    """.trimIndent()
)
