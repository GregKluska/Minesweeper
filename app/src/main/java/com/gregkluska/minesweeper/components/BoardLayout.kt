package com.gregkluska.minesweeper.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints

/**
 * Create a grid of n columns and fills it with minefields composables
 *
 * @param columns Number of columns
 * @param minefields Components to fill the grid
 */
@Composable
fun BoardLayout(
    columns: Int,
    minefields: @Composable () -> Unit,
) {
    if(columns < 1) {
        Log.e("Minesweeper", "Board: Columns cannot be less than 1")
        return
    }

    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val columnWidth = layoutWidth / columns

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
            val minefieldPlaceables = subcompose(0, minefields).map {
                it.measure(minefieldConstraints)
            }

            var r: Int = 0
            var c: Int = 0

            minefieldPlaceables.map {
                it.place(
                    x = c * columnWidth,
                    y = r * columnWidth,
                )

                if(c+1 >= columns) {
                    r += 1
                    c = 0
                } else {
                    c += 1
                }
            }
        }
    }
}