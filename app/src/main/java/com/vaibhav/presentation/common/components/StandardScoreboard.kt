package com.vaibhav.presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StandardScoreboard(
    modifier: Modifier = Modifier,
    player1Score: Int = 0,
    player2Score: Int = 0
) {
    Card(
        modifier = modifier
            .size(width = 100.dp, height = 50.dp),
        elevation = 16.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(
                            x = size.width * 0.5f,
                            y = size.height * 0.07f
                        ),
                        end = Offset(
                            x = size.width * 0.5f,
                            y = size.height * 0.93f
                        )
                    )
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = player1Score.toString(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )

            Text(
                text = player2Score.toString(),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}