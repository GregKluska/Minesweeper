package com.gregkluska.minesweeper

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    companion object {
        private const val TAG = "GameViewModel"
    }

    private val _gameState: MutableStateFlow<Game> = MutableStateFlow(Game())
    val gameState = _gameState.asStateFlow()

    private val game: Game
        get() = _gameState.value

    init {
        dispatch(Event.Welcome)
    }

    fun dispatch(event: Event) {
        when (event) {
            Event.Welcome -> onWelcome()
            Event.Start -> onStart()
            Event.Reset -> onReset()
            Event.Pause -> onPause()
            Event.Resume -> onResume()
            Event.GameOver -> onGameOver()
            is Event.OptionsUpdate -> onOptionsUpdate(event.options)
        }
    }

    private fun onWelcome() {}
    private fun onStart() {}
    private fun onReset() {}
    private fun onPause() {}
    private fun onResume() {}
    private fun onGameOver() {}

    private fun onOptionsUpdate(options: Options) {
        dispatchState(gameState = game.copy(options = options))
    }

    private fun dispatchState(gameState: Game) {
        _gameState.value = gameState
    }

}