package learn.algo.dsalgokt.ch06trees

class DepthFirstTraversalTodoTest : TraversalBaseTest(
    traversal = DepthFirstTraversalTodo(),
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
