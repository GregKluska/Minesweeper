package com.gregkluska.minesweeper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gregkluska.minesweeper.ui.theme.Green
import com.gregkluska.minesweeper.ui.theme.GreenLight

@Composable
fun Field() {

    val color = LocalColorScheme.current

    Field(
        grassColor = color.grass
    )
}

@Composable
private fun Field(
    grassColor: Color
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = grassColor
    ) {
    }
}

@Preview
@Composable
private fun FieldPreview() {
    Row(){
        Column(
            modifier = Modifier.size(200.dp)
        ) {
            Field(
                grassColor = Green
            )
        }
        Column(
            modifier = Modifier.size(200.dp)
        ) {
            Field(
                grassColor = GreenLight
            )
        }
    }
}