### Data Structures and Algorithms in Kotlin

* LinkedList (push, append, insert, pop, removeLast, removeAfter)
  * [LinkedListTodo](examples/src/main/kotlin/ch03linkedlist/LinkedListTodo.kt)
  * [LinkedList](examples/src/main/kotlin/ch03linkedlist/LinkedList.kt)
  * [test](examples/src/test/kotlin/ch03linkedlist/BasicLinkedListTest.kt)
* LinkedList Challenges
  * [1 - Reverse contents of a linked list](problems/src/main/kotlin/ch03linkedlist/c01reverse/IReversible.kt)
    * [Todo](problems/src/main/kotlin/ch03linkedlist/c01reverse/NodeReversibleTodo.kt)
    * [Solution](problems/src/main/kotlin/ch03linkedlist/c01reverse/NodeReversible.kt)
    * [test](problems/src/test/kotlin/ch03linkedlist/c01reverse/NodeReversibleBaseTest.kt)
  * [2 - The item in the middle](problems/src/main/kotlin/ch03linkedlist/c02middle/IMiddleFinder.kt)
    * [Todo](problems/src/main/kotlin/ch03linkedlist/c02middle/MiddleFinderTodo.kt)
    * [Solution](problems/src/main/kotlin/ch03linkedlist/c02middle/MiddleFinder.kt)
    * [test](problems/src/test/kotlin/ch03linkedlist/c02middle/MiddleFinderBaseTest.kt)
  * [3 - Reverse a linked list completely](problems/src/main/kotlin/ch03linkedlist/c03reverselist/INodeListReversible.kt)
    * [Todo](problems/src/main/kotlin/ch03linkedlist/c03reverselist/NodeReversibleTodo.kt)
    * [Solution](problems/src/main/kotlin/ch03linkedlist/c03reverselist/NodeReversible.kt)
    * [test](problems/src/test/kotlin/ch03linkedlist/c03reverselist/NodeListReversibleBaseTest.kt)
  * [4 - Merging two linked lists](problems/src/main/kotlin/ch03linkedlist/c04merging/INodeListMerger.kt)
    * [Todo](problems/src/main/kotlin/ch03linkedlist/c04merging/NodeListMergerTodo.kt)
    * [Solution](problems/src/main/kotlin/ch03linkedlist/c04merging/NodeListMerger.kt)
    * [test](problems/src/test/kotlin/ch03linkedlist/c04merging/NodeListMergerBaseTest.kt)
* Stack (push, pop, peek)
  * [example](examples/src/main/kotlin/ch04stack/Stack.kt)
  * [test](examples/src/test/kotlin/ch04stack/StackTest.kt)
* Stack Challenges
  * 1 - Print reversed linked list using stack - too simple. 
  * [2 - The parentheses validation](problems/src/main/kotlin/ch04stack/c02parentheses/IParenthesesBalance.kt)
    * [Todo](problems/src/main/kotlin/ch04stack/c02parentheses/ParenthesesBalanceTodo.kt)
    * [Solution](problems/src/main/kotlin/ch04stack/c02parentheses/ParenthesesBalance.kt)
    * [test](problems/src/test/kotlin/ch04stack/c02parentheses/ParenthesesBalanceBaseTest.kt)
* Queue (enqueue, dequeue)
  * [ArrayList implementation example](examples/src/main/kotlin/ch05queues/ArrayListQueue.kt)
  * [ArrayList implementation todo](examples/src/main/kotlin/ch05queues/ArrayListQueueTodo.kt)
  * [LinkedList implementation example](examples/src/main/kotlin/ch05queues/LinkedListQueue.kt)
  * [LinkedList implementation todo](examples/src/main/kotlin/ch05queues/LinkedListQueueTodo.kt)
  * [RingBuffer implementation example](examples/src/main/kotlin/ch05queues/RingBufferQueue.kt)
  * [RingBuffer implementation todo](examples/src/main/kotlin/ch05queues/RingBufferQueueTodo.kt)
  * [DoubleStack implementation example](examples/src/main/kotlin/ch05queues/DoubleStackQueue.kt)
  * [DoubleStack implementation todo](examples/src/main/kotlin/ch05queues/DoubleStackQueueTodo.kt)
  * [test](examples/src/test/kotlin/ch05queues/QueueBaseTest.kt)
* Trees
  * Depth first traversal
    * [Todo](examples/src/main/kotlin/ch06trees/DepthFirstTraversalTodo.kt)
    * [Example](examples/src/main/kotlin/ch06trees/DepthFirstTraversal.kt)
  * Level order traversal
    * [Todo](examples/src/main/kotlin/ch06trees/LevelOrderTraversalTodo.kt)
    * [Example](examples/src/main/kotlin/ch06trees/LevelOrderTraversal.kt)
  * [base test](examples/src/test/kotlin/ch06trees/TraversalBaseTest.kt)
* Binary Trees
  * [Convert binary tree to text diagram](common/src/main/kotlin/IBinaryNode.kt)
  * [test](common/src/test/kotlin/ITestNodeDiagramTest.kt)
  * Traversal algorithms
    * [In-order](examples/src/main/kotlin/ch07binarytrees/InOrderTraversal.kt)
    * [Pre-order](examples/src/main/kotlin/ch07binarytrees/PreOrderTraversal.kt)
    * [Post-order](examples/src/main/kotlin/ch07binarytrees/PostOrderTraversal.kt)
  * Traversal Challenges
    * 1 - The height of the tree
      * [Todo](problems/src/main/kotlin/ch07binarytrees/BinaryTreeHeightFinderTodo.kt)
      * [Solution](problems/src/main/kotlin/ch07binarytrees/BinaryTreeHeightFinder.kt)
      * [Test](problems/src/test/kotlin/ch07binarytrees/BinaryTreeHeightFinderBaseTest.kt)
    * 2 - Serialization of a Binary Tree
      * [Todo](problems/src/main/kotlin/ch07binarytrees/BinaryTreePreOrderSerializerTodo.kt)
      * [First Naive Solution](problems/src/main/kotlin/ch07binarytrees/BinaryTreePreOrderMyFirstSerializer.kt)
      * [Solution with traversal using Visitor](problems/src/main/kotlin/ch07binarytrees/BinaryTreePreOrderVisitorSerializer.kt)
      * [Test](problems/src/test/kotlin/ch07binarytrees/BinaryTreePreOrderSerializerBaseTest.kt)
