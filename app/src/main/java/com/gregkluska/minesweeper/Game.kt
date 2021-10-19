package com.gregkluska.minesweeper

import kotlin.random.Random
import kotlin.random.nextInt

data class Game(
    val state: State = State.Welcome,
    val options: Options = Options(),
    val flags: Set<Int> = setOf(),
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
                adjacentMines = getAdjacentMines(index, mines),
                flag = false
            )
        }
    }

    // First [options.columns] items -> first row
    private fun hasTop(index: Int): Boolean = index > options.columns
    private fun hasLeft(index: Int): Boolean = index.rem(options.columns) > 1
    private fun hasRight(index: Int): Boolean = index.rem(options.columns) != 0
    private fun hasBottom(index: Int): Boolean =
        index <= ((options.columns * options.rows) - options.columns)

    /**
     * Get index of the field above
     */
    private fun top(index: Int): Int? {
        return if (hasTop(index)) index - options.columns else null
    }

    /**
     * Get index of the field on the left
     */
    private fun left(field: Int): Int? {
        return if (hasLeft(field)) field - 1 else null
    }

    /**
     * Get index of the field on the right
     */
    private fun right(field: Int): Int? {
        return if (hasLeft(field)) field + 1 else null
    }

    /**
     * Get index of the field below
     */
    private fun bottom(field: Int): Int? {
        return if (hasBottom(field)) field - options.columns else null
    }

    private fun topLeft(index: Int): Int? {
        top(index)?.let { top ->
            return if (hasLeft(top)) top - 1 else null
        }
        return null

    }

    private fun topRight(index: Int): Int? {
        top(index)?.let { top ->
            return if (hasRight(top)) top + 1 else null
        }
        return null
    }


    private fun bottomLeft(index: Int): Int? {
        bottom(index)?.let { bottom ->
            return if (hasLeft(bottom)) bottom - 1 else null
        }
        return null
    }

    private fun bottomRight(index: Int): Int? {
        bottom(index)?.let { bottom ->
            return if (hasRight(bottom)) bottom + 1 else null
        }
        return null
    }

    /**
     * Count how many mines is around the field
     *
     * @param index Index of the field
     * @param mines Set of indices where mines are
     *
     * @return Adjacent mines
     */
    private fun getAdjacentMines(index: Int, mines: Set<Int>): Int {
        var count = 0

        if(top(index) in mines) count+=1
        if(topLeft(index) in mines) count+=1
        if(topRight(index) in mines) count+=1
        if(left(index) in mines) count+=1
        if(right(index) in mines) count+=1
        if(bottom(index) in mines) count+=1
        if(bottomLeft(index) in mines) count+=1
        if(bottomRight(index) in mines) count+=1

        return count
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
        if (rows < 1) throw IllegalArgumentException("Rows cannot be less than 1")
        if (columns < 1) throw IllegalArgumentException("Columns cannot be less than 1")
        if (mines > (rows * columns)) {
            throw IllegalArgumentException("Too many mines. Maximum value is rows*columns")
        }

    }
}

