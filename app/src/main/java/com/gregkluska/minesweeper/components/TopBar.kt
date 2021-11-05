package com.gregkluska.minesweeper.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gregkluska.minesweeper.R
import com.gregkluska.minesweeper.ui.theme.GreenDark

@Composable
fun TopBar(
    remainingFlags: Int = 0,
    onReset: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        color = GreenDark
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(0.3F)) {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(id = R.string.settings),
                            tint = White
                        )
                    }
                )
            }
            Row(
                modifier = Modifier.weight(0.4F),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.flag_icon),
                    modifier = Modifier.fillMaxHeight(0.6F),
                    contentDescription = stringResource(id = R.string.flag)
                )
                Text(
                    modifier = Modifier.weight(0.2F),
                    color = White,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Visible,
                    maxLines = 1,
                    text = remainingFlags.toString()
                )
                Image(
                    painter = painterResource(id = R.drawable.clock_icon),
                    modifier = Modifier.fillMaxHeight(0.6F),
                    contentDescription = stringResource(id = R.string.clock)
                )
                Text(
                    modifier = Modifier.weight(0.2F),
                    color = White,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Visible,
                    maxLines = 1,
                    text = remainingFlags.toString()
                )
            }
            Row(
                modifier = Modifier.weight(0.3F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = Icons.Filled.VolumeUp,
                            contentDescription = stringResource(id = R.string.volume),
                            tint = White
                        )
                    }
                )
            }

        }
    }
}

@Preview
@Composable
private fun TopBarPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            remainingFlags = 5555,
            onReset = {
                // do nothing
            }
        )
    }
}