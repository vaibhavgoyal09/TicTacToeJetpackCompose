package com.vaibhav.presentation.online_mode.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaibhav.presentation.common.components.ConfirmExitDialog
import com.vaibhav.presentation.common.components.GameBoard
import com.vaibhav.presentation.common.components.StandardScoreboard
import kotlinx.coroutines.flow.collect

@Composable
fun OnlineGameScreen(
    viewModel: OnlineGameViewModel = hiltViewModel(),
    isBackHandleEnabled: Boolean = false,
    scaffoldState: ScaffoldState,
    onNavigateUp: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is OnlineGameUiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

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

    val player1Name = viewModel.player1NameState.value
    val player2Name = viewModel.player2NameState.value
    val player1Score = viewModel.player1ScoreState.value
    val player2Score = viewModel.player2ScoreState.value

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
                .align(alignment = Alignment.Center)
        )
    }
}