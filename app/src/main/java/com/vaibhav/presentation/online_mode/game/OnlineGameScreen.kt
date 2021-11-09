package com.vaibhav.presentation.online_mode.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaibhav.presentation.common.components.GameBoard

@Composable
fun OnlineGameScreen(
    viewModel: OnlineGameViewModel = hiltViewModel(),
    isBackHandleEnabled: Boolean = false,
    onNavigateUp: () -> Unit
) {

    var shouldShowConfirmExitDialog by remember {
        mutableStateOf(false)
    }

    BackHandler(enabled = isBackHandleEnabled) {
        shouldShowConfirmExitDialog = true
    }

    if (shouldShowConfirmExitDialog) {
        AlertDialog(
            onDismissRequest = { shouldShowConfirmExitDialog = false },
            properties = DialogProperties(
                dismissOnBackPress = false
            ),
            confirmButton = {
                OutlinedButton(
                    onClick = { onNavigateUp() },
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface)
                ) {
                    Text(
                        text = "Yes",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { shouldShowConfirmExitDialog = false },
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface)
                ) {
                    Text(
                        text = "No",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            },
            title = {
                Text(
                    text = "Confirm Exit",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to exit the game?",
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface
                )
            }
        )
    }

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