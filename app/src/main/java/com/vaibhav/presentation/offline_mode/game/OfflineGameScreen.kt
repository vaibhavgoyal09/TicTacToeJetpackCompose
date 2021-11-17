package com.vaibhav.presentation.offline_mode.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaibhav.presentation.common.components.ConfirmExitDialog
import com.vaibhav.presentation.common.components.GameBoard
import com.vaibhav.presentation.common.components.StandardScoreboard
import com.vaibhav.presentation.common.components.StandardSettingsIcon
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OfflineGameScreen(
    viewModel: OfflineGameViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    isBackHandleEnabled: Boolean,
    player1Name: String,
    player2Name: String
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is OfflineScreenUiEvent.ShowWinDialog -> {
                    println("${event.winnerUserName} has won")
                }
                is OfflineScreenUiEvent.ShowMatchDrawDialog -> {
                    println("match draw")
                }
            }
        }
    }

    val player1Score = viewModel.player1ScoreState.value
    val player2Score = viewModel.player2ScoreState.value

    val boardPositionsList = viewModel.boardState.value

    var showConfirmExitDialog by remember {
        mutableStateOf(false)
    }

    BackHandler(enabled = isBackHandleEnabled) {
        showConfirmExitDialog = !showConfirmExitDialog
    }

    if (showConfirmExitDialog) {
        ConfirmExitDialog(
            onDismissRequest = { showConfirmExitDialog = false },
            onPositiveClick = { onNavigateUp() }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 20.dp)
                .align(alignment = Alignment.TopCenter),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = player1Name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )

            Spacer(
                modifier = Modifier.size(16.dp)
            )

            StandardScoreboard(
                player1Score = player1Score,
                player2Score = player2Score
            )

            Spacer(
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = player2Name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
        }

        GameBoard(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            gameState = boardPositionsList
        ) { position ->
            viewModel.onEvent(OfflineGameEvent.GameMove(position))
        }

        Box(
            modifier = Modifier
                .padding(bottom = 40.dp)
                .align(Alignment.BottomCenter)
        ) {
            StandardSettingsIcon(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}