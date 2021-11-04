package com.gregkluska.minesweeper.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import com.gregkluska.minesweeper.Game
import com.gregkluska.minesweeper.Options
import com.gregkluska.minesweeper.ui.theme.ColorScheme
import com.gregkluska.minesweeper.ui.theme.DarkColors
import com.gregkluska.minesweeper.ui.theme.LightColors

/**
 * Color provider for fields
 */
val LocalColorScheme = compositionLocalOf<ColorScheme> { error("Missing colors") }

/**
 * Component holder
 */
data class BoardItem(
    val component: @Composable () -> Unit
)

@Composable
fun Board(
    options: Options = Options(),
    fields: List<BoardItem>
) {

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

            val isColumnEven = options.columns.rem(2) == 0

            fields.forEachIndexed { index, item ->
                val component: @Composable () -> Unit = {
                    val isRowNumEven = (index.floorDiv(options.columns)).rem(2) == 0
                    val isIndexEven = index.rem(2) == 0

                    val darkLight = if (!isIndexEven) DarkColors else LightColors
                    val lightDark = if (!isIndexEven) LightColors else DarkColors

                    val color: ColorScheme =
                        if (!isColumnEven) darkLight else if (!isRowNumEven) darkLight else lightDark

                    /**
                     * Provide color.
                     * If the columns number is odd then in every row colors are Dark - Light - Dark..
                     * Otherwise the order of colors needs to be changed for every next row
                     */
                    CompositionLocalProvider(LocalColorScheme provides color) {
                        item.component()
                    }
                }

                val minefieldPlaceables = subcompose(index, component).map {
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
    val fields = MutableList(100) { BoardItem { Field( state = Game.FieldState.Close, mine = false, number = 0 ) } }
    val options = Options(columns = 10)

    Board(
        options = options,
        fields = fields
    )
}
