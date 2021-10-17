package com.gregkluska.minesweeper

sealed class Event {

    object Welcome : Event()
    object Start : Event()
    object Pause : Event()
    object Resume : Event()
    object Reset : Event()
    object GameOver : Event()

    data class OptionsUpdate(
        val options: Options
    ): Event()

}
