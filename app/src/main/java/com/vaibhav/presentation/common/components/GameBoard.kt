package com.vaibhav.presentation.common.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vaibhav.presentation.common.theme.GameBoardBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    onItemClick: (position: Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(300.dp)
            .padding(10.dp)
            .clickable(enabled = false) {},
        elevation = 16.dp,
        backgroundColor = GameBoardBackground,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawBoardLines(size)
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {

            }
        }
    }
}

@Composable
fun StandardImageView(
    @DrawableRes imageResId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(33.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

private fun DrawScope.drawBoardLines(size: Size) {
    drawLine(
        color = Color.Gray,
        start = Offset(
            x = size.width * .33f,
            y = size.height * .03f
        ),
        end = Offset(
            x = size.width * .33f,
            y = size.height * .97f
        )
    )

    drawLine(
        color = Color.Gray,
        start = Offset(
            x = size.width * .66f,
            y = size.height * .03f
        ),
        end = Offset(
            x = size.width * .66f,
            y = size.height * .97f
        )
    )

    drawLine(
        color = Color.Gray,
        start = Offset(
            x = size.width * .03f,
            y = size.height * .33f
        ),
        end = Offset(
            x = size.width * .97f,
            y = size.height * .33f
        )
    )

    drawLine(
        color = Color.Gray,
        start = Offset(
            x = size.width * .03f,
            y = size.height * .66f
        ),
        end = Offset(
            x = size.width * .97f,
            y = size.height * .66f
        )
    )
}