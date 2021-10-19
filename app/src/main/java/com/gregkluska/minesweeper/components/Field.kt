package com.gregkluska.minesweeper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gregkluska.minesweeper.ui.theme.Green
import com.gregkluska.minesweeper.ui.theme.GreenLight

@Composable
fun Field(
    color: Color
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = color
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
                color = Green
            )
        }
        Column(
            modifier = Modifier.size(200.dp)
        ) {
            Field(
                color = GreenLight
            )
        }
    }
}