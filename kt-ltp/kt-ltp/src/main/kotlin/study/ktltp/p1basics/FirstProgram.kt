package study.ktltp.p1basics

import kotlinx.coroutines.*
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.SwingUtilities
import kotlin.time.Duration.Companion.milliseconds

private val zeroTime = System.currentTimeMillis()
private fun log(message: Any?) =
    println("${System.currentTimeMillis() - zeroTime} [${Thread.currentThread().name}] $message")

private suspend fun performBackgroundOperation(): String {
    log("in performBackgroundOperation")
    delay(100.milliseconds)
    return "result"
}

private fun updateUI(result: String) {
    log("in updateUI with result=$result")
}

fun main() {
    // Run with: -Dkotlinx.coroutines.debug
    runBlocking {
        log("in runBlocking")
        launch(Dispatchers.Default) {
            log("in launch")
            val result = performBackgroundOperation()
            withContext(Dispatchers.Main) {
                updateUI(result)
            }
            log("end launch")
        }
        log("end runBlocking")
    }
    log("start SwingUtilities.invokeLater...")
    /*
Output:
74 [main @coroutine#1] in runBlocking
81 [main @coroutine#1] end runBlocking
81 [DefaultDispatcher-worker-1 @coroutine#2] in launch
82 [DefaultDispatcher-worker-1 @coroutine#2] in performBackgroundOperation
323 [AWT-EventQueue-0 @coroutine#2] in updateUI with result=result
323 [DefaultDispatcher-worker-1 @coroutine#2] end launch
323 [main] start SwingUtilities.invokeLater...
     */

    SwingUtilities.invokeLater { FirstProgram().doLaunch() }
}

internal class FirstProgram {
    fun doLaunch() {
        val frame = JFrame("Basics")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.iconImage = ImageIcon(getIconImageBytes()).image
        val panel = TilePanel(
            createTileColors(
                shader = Shader.from("dots"),
                size = 16,
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

const val MAX_INTENCITY = 255

enum class GRAY_INTENCITY(val v: Int) {
    WHITE(MAX_INTENCITY),
    LIGHT_GRAY(MAX_INTENCITY / 4 * 3),
    MIDDLE_GRAY(MAX_INTENCITY / 2),
    DARK_GRAY(MAX_INTENCITY / 4),
    BLACK(0);
}
