package com.gregkluska.minesweeper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gregkluska.minesweeper.components.Board
import com.gregkluska.minesweeper.components.BoardItem
import com.gregkluska.minesweeper.components.Field
import com.gregkluska.minesweeper.ui.theme.MinesweeperTheme

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val gameState = viewModel.gameState.collectAsState()

            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.dispatch(Event.Start) }) { Text("Start") }
                    Button(onClick = { viewModel.dispatch(Event.Reset) }) { Text("Reset") }
                }

                val fields: MutableList<BoardItem> = mutableListOf()

                gameState.value.fields.forEachIndexed { index, field ->
                    fields.add(index, BoardItem {
                        Field(
                            state = field.state,
                            mine = field.mine,
                            number = field.adjacentMines,
                            onClick = { viewModel.dispatch(Event.Click(index)) }
                        )
                    })
                }

                Board(
                    options = gameState.value.options,
                    fields = fields
                )

            }
        }
    }
}
