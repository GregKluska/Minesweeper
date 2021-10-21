package com.gregkluska.minesweeper.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import com.gregkluska.minesweeper.Options
import com.gregkluska.minesweeper.ui.theme.Green
import com.gregkluska.minesweeper.ui.theme.GreenLight

interface BoardScope {
    fun field(content: @Composable () -> Unit)
}

private data class BoardItem(
    val content: @Composable () -> Unit
)

private class BoardScopeImpl : BoardScope {

    val fieldsss: MutableList<BoardItem> = mutableListOf()

    override fun field(content: @Composable () -> Unit) {
        fieldsss.add(element = BoardItem(content))
    }

}

@Composable
fun Board(
    options: Options = Options(),
    fields: BoardScope.() -> Unit
) {

    val items = BoardScopeImpl().apply { fields() }

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val columnWidth = layoutWidth / options.columns

        /**
         * Constraints to force them to be squares
         */
        val minefieldConstraints = Constraints(
            minWidth = columnWidth,
            maxWidth = columnWidth,
            minHeight = columnWidth,
            maxHeight = columnWidth
        )

        layout(layoutWidth, layoutHeight) {
            var r: Int = 0
            var c: Int = 0

            items.fieldsss.forEachIndexed { index, item ->
                val minefieldPlaceables = subcompose(index, item.content).map {
                    it.measure(minefieldConstraints)
                }

                minefieldPlaceables.map {
                    it.place(
                        x = c * columnWidth,
                        y = r * columnWidth,
                    )
                }

                if (c + 1 >= options.columns) {
                    r += 1
                    c = 0
                } else {
                    c += 1
                }
            }

        }

    }
}

@Preview
@Composable
private fun BoardPreview() {

    val options = Options(columns = 10)
    Board(
        options = options
    ) {
        for(i in 0 until 100) {
            // Todo: Think of a better way of getting colors.
            val row = ( i.floorDiv(options.columns) )

            val color1 = if(i.rem(2) > 0) Green else GreenLight
            val color2 = if(i.rem(2) > 0) GreenLight else Green

            val color = if(options.columns.rem(2)>0) color1 else if(row.rem(2) > 0) color1 else color2

            field {
                Field(
                    color = color
                )
            }


        }
    }
}
