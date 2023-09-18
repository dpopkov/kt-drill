package study.ktltp.p1basics

import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.SwingUtilities

fun main() {
    SwingUtilities.invokeLater { FirstProgram().doLaunch() }
}

internal class FirstProgram {
    fun doLaunch() {
        val frame = JFrame("Basics")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.iconImage = ImageIcon(getIconImageBytes()).image
        val panel = TilePanel(
            createTileColors(
//                shader = Shader.from("zebra"),
                shader = StairsShader(
                    style = object : StairsStyle {
                        override val stairsColor = GRAY_INTENCITY.BLACK
                        override val airColor = GRAY_INTENCITY.WHITE
                        private val blackIndices = listOf(1, 6)

                        override fun shadeAsStairsPredicate(columnIdx: Int, rowIdx: Int): Boolean {
                            return rowIdx in blackIndices && columnIdx in blackIndices
                        }

                    }
                ),
                size = 8,
            )
        )
        frame.add(panel)
        frame.pack()
        frame.isVisible = true
    }

    private fun getIconImageBytes(): ByteArray {
        var bytes: ByteArray? = null
        FirstProgram::class.java.classLoader.getResourceAsStream("icon.png")?.use {
            bytes = it.readAllBytes()
        }
        return bytes ?: error("Error reading icon file")
    }

    /** Создает двумерный массив цветов, используя правила заданные шейдером */
    private fun createTileColors(shader: Shader, size: Int = 12): Array<Array<Int>> {
        return Array(size) {
            Array(size) { 0 }
        }.also {
            shader.shadeCells(it)
        }
    }
}

/** Определяет стиль лестницы */
internal interface StairsStyle {
    val stairsColor: GRAY_INTENCITY
    val airColor: GRAY_INTENCITY
    fun shadeAsStairsPredicate(columnIdx: Int, rowIdx: Int): Boolean

    companion object {
        val SOLID_BLACK = object : StairsStyle {
            override val stairsColor = GRAY_INTENCITY.BLACK
            override val airColor = GRAY_INTENCITY.WHITE
            override fun shadeAsStairsPredicate(columnIdx: Int, rowIdx: Int): Boolean = columnIdx <= rowIdx
        }
        val THIN_GRAY = object : StairsStyle {
            override val stairsColor = GRAY_INTENCITY.DARK_GRAY
            override val airColor = GRAY_INTENCITY.LIGHT_GRAY
            override fun shadeAsStairsPredicate(columnIdx: Int, rowIdx: Int): Boolean = columnIdx == rowIdx
        }
    }
}

enum class GRAY_INTENCITY(val v: Int) {
    WHITE(255),
    LIGHT_GRAY(240),
    MIDDLE_GRAY(140),
    DARK_GRAY(40),
    BLACK(0);
}
