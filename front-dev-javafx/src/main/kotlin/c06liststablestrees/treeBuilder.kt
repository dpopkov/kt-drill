package learn.javafx.c06liststablestrees

import javafx.scene.control.TreeItem

interface Element {
    var label: String
    fun composeInSubTreeOf(parentTreeItem: TreeItem<String>)
}

abstract class Tag() : Element {
    private val children = mutableListOf<Element>()
    override var label: String = ""

    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun composeInSubTreeOf(parentTreeItem: TreeItem<String>) {
        for (child in children) {
            parentTreeItem.children.add(
                TreeItem(child.label).also {
                    child.composeInSubTreeOf(it)
                })
        }
    }

    fun build() = TreeItem(label).also { composeInSubTreeOf(it) }
}

abstract class TagBase() : Tag() {
    fun item(label: String, init: (Item.() -> Unit) = {}) {
        val item: Item = initTag(Item(), init)
        item.label = label
    }
}

class Tree : TagBase()
class Item : TagBase()

fun tree(init: Tree.() -> Unit) = Tree().also { it.init() }