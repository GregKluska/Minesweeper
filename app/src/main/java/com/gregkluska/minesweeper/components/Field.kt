package com.gregkluska.minesweeper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gregkluska.minesweeper.Game
import com.gregkluska.minesweeper.R
import com.gregkluska.minesweeper.ui.theme.Brown
import com.gregkluska.minesweeper.ui.theme.BrownLight
import com.gregkluska.minesweeper.ui.theme.Green
import com.gregkluska.minesweeper.ui.theme.GreenLight

@Composable
fun Field(
    state: Game.FieldState,
    mine: Boolean,
    number: Int,
    onClick: () -> Unit = {},
) {

    val color = LocalColorScheme.current

    Field(
        state = state,
        mine = mine,
        number = number,
        grassColor = color.grass,
        dirtColor = color.dirt,
        onClick = onClick
    )
}

@Composable
private fun Field(
    state: Game.FieldState,
    mine: Boolean,
    number: Int = 0,
    grassColor: Color,
    dirtColor: Color,
    onClick: () -> Unit = {},
) {
    if(state == Game.FieldState.Open && mine) {
        // Bomb
        Mine()
        return
    }

    val color: Color = when (state) {
        Game.FieldState.Open -> dirtColor
        Game.FieldState.Close -> grassColor
        Game.FieldState.Flag -> grassColor
    }

    Surface(
        modifier = Modifier.fillMaxSize().clickable(onClick = onClick),
        color = color
    ) {
        if (state == Game.FieldState.Flag) {
            Image(
                painter = painterResource(id = R.drawable.flag_icon), 
                contentDescription = stringResource(id = R.string.flag)
            )
        }
        if(state == Game.FieldState.Open) {
            Text(text = "${if(number == 0)"" else number.toString()}")
        }
    }
}

@Preview
@Composable
private fun FieldPreview() {
    Column() {
        Row() {
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Close,
                    mine = false,
                    grassColor = Green,
                    dirtColor = Brown
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Close,
                    mine = false,
                    grassColor = GreenLight,
                    dirtColor = BrownLight
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Open,
                    mine = false,
                    grassColor = Green,
                    dirtColor = Brown
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Open,
                    mine = false,
                    grassColor = GreenLight,
                    dirtColor = BrownLight
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Flag,
                    mine = false,
                    grassColor = Green,
                    dirtColor = Brown
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Flag,
                    mine = false,
                    grassColor = GreenLight,
                    dirtColor = BrownLight
                )
            }
        }
        Row() {
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Close,
                    mine = true,
                    grassColor = Green,
                    dirtColor = Brown
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Close,
                    mine = true,
                    grassColor = GreenLight,
                    dirtColor = BrownLight
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Open,
                    mine = true,
                    grassColor = Green,
                    dirtColor = Brown
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Open,
                    mine = true,
                    grassColor = GreenLight,
                    dirtColor = BrownLight
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Flag,
                    mine = true,
                    grassColor = Green,
                    dirtColor = Brown
                )
            }
            Column(
                modifier = Modifier.size(50.dp)
            ) {
                Field(
                    state = Game.FieldState.Flag,
                    mine = true,
                    grassColor = GreenLight,
                    dirtColor = BrownLight
                )
            }
        }
    }
}