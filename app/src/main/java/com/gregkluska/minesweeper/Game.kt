package com.gregkluska.minesweeper

import kotlin.random.Random
import kotlin.random.nextInt

data class Game(
    val state: State = State.Welcome,
    val options: Options = Options(),
    val flags: Set<Int>,
) {

    val fields: List<Field>

    init {
        if (options.mines > (options.rows * options.columns)) {
            throw IllegalArgumentException("Too many mines. Maximum value is rows*columns")
        }

        val mines = randomMines(options = options)

        fields = List(options.rows * options.columns) { index ->
            Field(
                mine = index in mines,
                adjacentMines = getAdjacentMines(index, mines, options),
                flag = false
            )
        }
    }

    companion object {

        /**
         * Generate set of n mines in field of x rows and y columns
         *
         * @param rows number of rows
         * @param columns number of columns
         * @param mineCount number of mines
         * @return Set of mines
         */
        private fun randomMines(options: Options): Set<Int> {
            val mines = HashSet<Int>(options.mines)

            while (mines.size < options.mines) {
                mines += Random.nextInt(range = 1..(options.rows * options.columns))
            }

            return mines
        }

        private fun getAdjacentMines(index: Int, mines: Set<Int>, options: Options): Int {
            var count = 0


        }

        private fun top(target: Int, options: Options): Int? {
            
        }
    }

    data class Field(
        val mine: Boolean,
        val adjacentMines: Int,
        val flag: Boolean
    )

    enum class State { Welcome, Running, Paused, GameOver }
}

data class Options(
    val rows: Int = 10,
    val columns: Int = 10,
    val mines: Int = 10
) {
    init {
        if(rows < 1) throw IllegalArgumentException("Rows cannot be less than 1")
        if(columns < 1) throw IllegalArgumentException("Columns cannot be less than 1")
        if (mines > (rows * columns)) {
            throw IllegalArgumentException("Too many mines. Maximum value is rows*columns")
        }

    }
}

