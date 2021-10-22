package com.gregkluska.minesweeper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gregkluska.minesweeper.Game
import com.gregkluska.minesweeper.ui.theme.*
import kotlin.random.Random

@Composable
fun Mine() {
    val colors = listOf(Red, Pink, Purple, Orange, OrangeLight, Blue, BlueLight, GreenAlt)
    val rand = Random.nextInt(colors.size)

    Mine(color = colors[rand])
}

@Composable
private fun Mine(
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(0.55F),
            shape = CircleShape,
            color = MineColor
        ) {}
    }
}

@Preview
@Composable
private fun MinePreview() {
    Row() {
        val colors = listOf(Red, Pink, Purple, Orange, OrangeLight, Blue, BlueLight, GreenAlt)

        for(i in 0..5) {
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Mine(
                    color = colors[i]
                )
            }
        }
    }
}