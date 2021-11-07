package com.gregkluska.minesweeper

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.zIndex
import com.gregkluska.minesweeper.components.Board
import com.gregkluska.minesweeper.components.BoardItem
import com.gregkluska.minesweeper.components.Field
import com.gregkluska.minesweeper.components.TopBar
import com.gregkluska.minesweeper.ui.theme.GreenDark

class MainActivity : ComponentActivity() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val gameState = viewModel.gameState.collectAsState()

            Column(
                modifier = Modifier.background(GreenDark)
            ) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(999F)) {
                    TopBar(
                        remainingFlags = 5, onReset = {}
                    )
                }


                val fields: MutableList<BoardItem> = mutableListOf()

                gameState.value.fields.forEachIndexed { y, row ->
                    row.forEachIndexed { x, field ->

                        fields.add(BoardItem {
                            Field(
                                state = field.state,
                                mine = field.mine,
                                number = field.adjacentMines,
                                onClick = {
                                    Log.d("AppDebug", "onCreate: Clicked")
                                    viewModel.dispatch(Event.Click(field.x, field.y))
                                },
                                onLongClick = {
                                    viewModel.dispatch(Event.LongClick(field.x, field.y))
                                }
                            )
                        })
                    }

                }

                val width = remember { mutableStateOf(0) }
                val height = remember { mutableStateOf(0) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            width.value = it.size.width
                            height.value = it.size.height
                        }
                ) {


                    val scale = remember { mutableStateOf(1f) }
                    val offsetX = remember { mutableStateOf(0f) }
                    val offsetY = remember { mutableStateOf(0f) }



                    val maxOffsetX = remember(scale) {
                        derivedStateOf { (width.value * scale.value) / 2F - (width.value / 2F) }
                    }
                    val minOffsetX = remember(maxOffsetX) {
                        derivedStateOf { ((maxOffsetX.value) * (-1)) }
                    }

                    val maxOffsetY = remember(scale) {
                        derivedStateOf { (height.value * scale.value) / 2F - (height.value / 2F) }
                    }
                    val minOffsetY = remember(maxOffsetY) {
                        derivedStateOf { ((maxOffsetY.value) * (-1)) }
                    }

                    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                        if (scale.value * (zoomChange) < 0.7F) {
                            scale.value = 0.7F
                        } else {
                            scale.value *= (zoomChange)
                        }

                        offsetX.value = when {
                            scale.value < 1F -> 0F
                            offsetX.value + offsetChange.x > maxOffsetX.value -> maxOffsetX.value
                            offsetX.value + offsetChange.x < minOffsetX.value -> minOffsetX.value
                            else -> offsetX.value + offsetChange.x
                        }

                        offsetY.value = when {
                            scale.value < 1F -> 0F
                            offsetY.value + offsetChange.y > maxOffsetY.value -> maxOffsetY.value
                            offsetY.value + offsetChange.y < minOffsetY.value -> minOffsetY.value
                            else -> offsetY.value + offsetChange.y
                        }

                    }

                    Board(
                        modifier = Modifier
                            .graphicsLayer(
                                scaleX = scale.value,
                                scaleY = scale.value,
                                translationX = offsetX.value,
                                translationY = offsetY.value
                            )
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consumeAllChanges()
                                    Log.d("AppDebug", "maxOffseY ${maxOffsetY.value}")
                                    offsetX.value = when {
                                        scale.value < 1F -> 0F
                                        offsetX.value + dragAmount.x > maxOffsetX.value -> maxOffsetX.value
                                        offsetX.value + dragAmount.x < minOffsetX.value -> minOffsetX.value
                                        else -> offsetX.value + dragAmount.x
                                    }

                                    offsetY.value = when {
                                        scale.value < 1F -> 0F
                                        offsetY.value + dragAmount.y > maxOffsetY.value -> maxOffsetY.value
                                        offsetY.value + dragAmount.y < minOffsetY.value -> minOffsetY.value
                                        else -> offsetY.value + dragAmount.y
                                    }
                                }
                            }
                            .transformable(state = state)
                            .fillMaxWidth(),
                        options = gameState.value.options,
                        fields = fields
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { viewModel.dispatch(Event.Start) }) { Text("Start") }
                    Button(onClick = { viewModel.dispatch(Event.Reset) }) { Text("Reset") }
                }

            }
        }
    }
}
