package study.ktltp.p1basics

import kotlin.math.min

/** Заполняет двумерный массив в соответствии с реализацией интерфейса */
internal interface Shader {
    fun shadeCells(cells: Array<Array<Int>>)

    companion object {
        fun from(name: String) = when(name.lowercase()) {
            "checker" -> CheckerShader()
            "gradient" -> GradientShader()
            "zebra" -> ZebraShader()
            "solid stairs" -> StairsShader(StairsStyle.SOLID_BLACK)
            "thin stairs" -> StairsShader(StairsStyle.THIN_GRAY)
            else -> { error("Неизвестный шейдер") }
        }
    }
}

internal class CheckerShader() : Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        for (r in cells.indices) {
            for (c in 0 until cells[r].size) {
                if ((r + c) % 2 == 0) {
                    cells[r][c] = GRAY_INTENCITY.WHITE.v
                } else {
                    cells[r][c] = GRAY_INTENCITY.DARK_GRAY.v
                }
            }
        }
    }
}

internal class GradientShader() : Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        var shadeValue = 0
        val addition = 255 / cells.size
        cells.forEach {row ->
            row.fill(shadeValue)
            shadeValue = min(shadeValue + addition, 255)
        }
    }
}

internal class ZebraShader(): Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        for (r in cells.indices) {
            cells[r].fill(if (r % 2 == 0) 0 else 255)
        }
    }
}

internal class StairsShader(private val style: StairsStyle) : Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        val stair = style.stairsColor.v
        val air = style.airColor.v
        for (r in cells.indices) {
            for (c in 0 until cells[r].size) {
                cells[r][c] = if (style.shadeAsStairsPredicate(c, r)) stair else air
            }
        }
    }
}
