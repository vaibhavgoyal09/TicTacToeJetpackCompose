package com.vaibhav.presentation.online_mode.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaibhav.presentation.common.components.GameBoard

@Composable
fun OnlineGameScreen(
    viewModel: OnlineGameViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        GameBoard(
            modifier = Modifier
                .align(alignment = Alignment.Center)
        )
    }
}