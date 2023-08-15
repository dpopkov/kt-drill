package learn.algo.dsalgokt.ch03linkedlist

import learn.algo.dsalgokt.common.example

fun main() {
    "Node" example {
        val n1 = Node(value = 1)
        val n2 = Node(value = 2)
        val n3 = Node(value = 3)
        n1.next = n2
        n2.next = n3
        println(n1)
    }
    "LinkedList push" example {
        val list = LinkedList<Int>()
        list.push(11).push(22).push(33)
        println(list)
    }
    "LinkedList append" example {
        val list = LinkedList<Int>()
        list.append(11).append(22).append(33)
        println(list)
    }
    "LinkedList insert" example {
        val list = LinkedList<Int>()
        list.push(33).push(22).push(11)
        var after = list.nodeAt(1)
        check(after != null)
        after = list.insert(23, after)
        after = list.insert(24, after)
        list.insert(25, after)
        println(list)
    }
    "LinkedList pop" example {
        val list = LinkedList<Int>()
        list.push(3).push(2).push(1)
        println("Before popping: $list")
        val popped = list.pop()
        println("After popping: $list")
        println("Popped value: $popped")
    }
    "LinkedList removeLast" example {
        val list = LinkedList<Int>()
        list.push(3).push(2).push(1)
        println("Before removing: $list")
        val removed = list.removeLast()
        println("After removing: $list")
        println("Removed value: $removed")
    }
    "LinkedList removeAfter" example {
        val list = LinkedList<Int>()
        list.push(3).push(2).push(1)
        println("Before removing: $list")
        val removed = list.removeAfter(0)
        println("After removing: $list")
        println("Removed value: $removed")
    }
}
