package study.ktltp.p1basics

import kotlin.math.min

/** Заполняет двумерный массив в соответствии с реализацией интерфейса */
internal interface Shader {
    fun shadeCells(cells: Array<Array<Int>>)

    companion object {
        fun from(name: String) = when(name.lowercase()) {
            "checker", "checker-white" -> CheckerShader()
            "checker-black" -> CheckerShader(startWithWhite = false)
            "gradient" -> GradientShader()
            "gradient-diagonal" -> GradientByDiagonal()
            "zebra" -> ZebraShader()
            "zebra-vertical" -> ZebraVerticalShader()
            "dots" -> DotsShader()
            "solid stairs" -> StairsShader(StairsStyle.SOLID_BLACK)
            "thin stairs" -> StairsShader(StairsStyle.THIN_GRAY)
            else -> { error("Неизвестный шейдер") }
        }
    }
}

/** Закрашивает клетки по образцу шахматной доски */
internal class CheckerShader(val startWithWhite: Boolean = true) : Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        val evenColor: Int = if (startWithWhite) GRAY_INTENCITY.WHITE.v else GRAY_INTENCITY.DARK_GRAY.v
        val oddColor: Int = if (startWithWhite) GRAY_INTENCITY.DARK_GRAY.v else GRAY_INTENCITY.WHITE.v
        for (r in cells.indices) {
            for (c in 0 until cells[r].size) {
                if ((r + c) % 2 == 0) {
                    cells[r][c] = evenColor
                } else {
                    cells[r][c] = oddColor
                }
            }
        }
    }
}

internal class GradientShader() : Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        var shadeValue = 0
        val addition = MAX_INTENCITY / cells.size
        cells.forEach {row ->
            row.fill(shadeValue)
            shadeValue = min(shadeValue + addition, MAX_INTENCITY)
        }
    }
}

/** Градиент от левого верхнего угла до правого нижнего */
internal class GradientByDiagonal() : Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        val maxProductOfIndices = (cells.lastIndex * cells[0].lastIndex).toDouble()
        for (r in cells.indices) {
            for (c in 0 until cells[r].size) {
                cells[r][c] = valueForCell(r, c, maxProductOfIndices)
            }
        }
    }

    private fun valueForCell(rowIdx: Int, columnIdx: Int, maxProductOfIndices: Double) =
        ((rowIdx * columnIdx / maxProductOfIndices) * MAX_INTENCITY).toInt()

}

internal class ZebraShader(): Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        for (r in cells.indices) {
            cells[r].fill(if (r % 2 == 0) 0 else MAX_INTENCITY)
        }
    }
}

internal class ZebraVerticalShader(): Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        for (row in cells) {
            for (c in row.indices) {
                row[c] = if (c % 2 == 0) 0 else MAX_INTENCITY
            }
        }
    }

}

internal class DotsShader(): Shader {
    override fun shadeCells(cells: Array<Array<Int>>) {
        for (rowIdx in cells.indices) {
            for (columnIdx in cells[rowIdx].indices) {
                cells[rowIdx][columnIdx] = if (rowIdx * columnIdx % 2 == 0) MAX_INTENCITY else 0
            }
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
