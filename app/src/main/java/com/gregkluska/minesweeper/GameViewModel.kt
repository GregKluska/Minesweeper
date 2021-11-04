package com.gregkluska.minesweeper

import android.util.Log
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
            is Event.Click -> onClick(event.index)
            is Event.LongClick -> onLongClick(event.index)
        }
    }

    private fun onClick(index: Int) {
        Log.d(TAG, "onClick: called with index = $index")

        if(game.state != Game.State.Running) return

        val field = game.fields[index] // IndexOutOfBoundsException

        if(field.state == Game.FieldState.Close) {
            Log.d(TAG, "Field: $field")


            val fields = game.openField(index)
            Log.d(TAG, "Fields: $fields")
            dispatchState(
                game.copy(
                    fields = fields,
//                    fields = game.fields.toMutableList().apply { this[0] = this[0].copy(state = Game.FieldState.Open) },
                    state = if(field.mine) Game.State.GameOver else game.state,
                )
            )
        }
    }

    private fun onLongClick(index: Int) {
        Log.d(TAG, "onLongClick: called with index = $index")

        if(game.state != Game.State.Running) return

        val flags = game.flags.toMutableSet()
        flags.toggle(index)

        dispatchState(
            game.copy(
                flags = flags
            )
        )

    }

    private fun onWelcome() {
        Log.d(TAG, "onWelcome: called")
        dispatchState(gameState = Game())
    }

    private fun onStart() {
        Log.d(TAG, "onStart: called")
        if (game.state == Game.State.Welcome) {
            dispatchState(gameState = game.copy(state = Game.State.Running))
        }
    }

    private fun onReset() {
        Log.d(TAG, "onReset: called")
        dispatchState(Game(options = game.options))
    }

    private fun onPause() {
        Log.d(TAG, "onPause: called")
        // Todo: State check. onPause can be called on running game only
        if (game.state == Game.State.Running) {
            dispatchState(game.copy(state = Game.State.Paused))
        }
    }

    private fun onResume() {
        Log.d(TAG, "onResume: called")
        // Todo: State check. onResume can be called on paused game only
        if(game.state == Game.State.Paused) {
            dispatchState(game.copy(state = Game.State.Running))
        }
    }

    private fun onGameOver() {
        Log.d(TAG, "onGameOver: called")
        // Todo: state check. onGameOver can be called on running game only
        if(game.state == Game.State.Running) {
            dispatchState(game.copy(state = Game.State.GameOver))
        }
    }

    private fun onOptionsUpdate(options: Options) {
        Log.d(TAG, "onOptionsUpdate: called with options = $options")
        // It's basically reset with custom options
        dispatchState(gameState = game.copy(state = Game.State.Welcome, options = options))
    }

    private fun dispatchState(gameState: Game) {
        _gameState.value = gameState
    }

}