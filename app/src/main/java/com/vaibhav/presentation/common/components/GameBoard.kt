package com.vaibhav.presentation.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vaibhav.R
import com.vaibhav.presentation.common.theme.GameBoardBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GameBoard(
    modifier: Modifier = Modifier,
    gameState: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
    onItemClick: (position: Int) -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(300.dp)
            .padding(10.dp),
        elevation = 16.dp,
        backgroundColor = GameBoardBackground,
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind { drawBoardLines(size) }
        ) {

            StandardBoardItem(
                itemState = gameState[0],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.TopStart)
            ) {
                onItemClick(0)
            }

            StandardBoardItem(
                itemState = gameState[1],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.TopCenter)
            ) {
                onItemClick(1)
            }

            StandardBoardItem(
                itemState = gameState[2],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.TopEnd)
            ) {
                onItemClick(2)
            }

            StandardBoardItem(
                itemState = gameState[3],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.CenterStart)
            ) {
                onItemClick(3)
            }

            StandardBoardItem(
                itemState = gameState[4],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.Center)
            ) {
                onItemClick(4)
            }

            StandardBoardItem(
                itemState = gameState[5],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.CenterEnd)
            ) {
                onItemClick(5)
            }

            StandardBoardItem(
                itemState = gameState[6],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.BottomStart)
            ) {
                onItemClick(6)
            }

            StandardBoardItem(
                itemState = gameState[7],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.BottomCenter)
            ) {
                onItemClick(7)
            }

            StandardBoardItem(
                itemState = gameState[8],
                modifier = Modifier
                    .fillMaxSize(0.33f)
                    .align(Alignment.BottomEnd)
            ) {
                onItemClick(8)
            }
        }
    }
}

@Composable
fun StandardBoardItem(
    modifier: Modifier = Modifier,
    itemState: Int = 0,
    onClick: () -> Unit = {}
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
    ) {
        when (itemState) {
            1 -> {
                Image(
                    painter = painterResource(id = R.drawable.x),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            2 -> {
                Image(
                    painter = painterResource(id = R.drawable.o),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            else -> Unit
        }
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