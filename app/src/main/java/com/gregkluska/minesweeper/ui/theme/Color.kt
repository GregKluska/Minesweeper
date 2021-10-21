package com.gregkluska.minesweeper.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

/** Minesweeper colors */
val GreenLight = Color(0xFFaad751)
val GreenDark = Color(0xFF4a752c)
val Green = Color(0xFFa2d149)
val Brown = Color(0xFFd7b899)
val BrownLight = Color(0xFFe5c29f)


data class ColorScheme(
    val grass: Color,
    val dirt: Color
)

val DarkColors = ColorScheme(
    grass = Green,
    dirt = Brown
)

val LightColors = ColorScheme(
    grass = GreenLight,
    dirt = BrownLight
)