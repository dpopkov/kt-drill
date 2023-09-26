package learn.algo.dsalgokt.ch06trees

class LevelOrderTraversalTodoTest : TraversalBaseTest(
    traversal = LevelOrderTraversalTodo(),
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
