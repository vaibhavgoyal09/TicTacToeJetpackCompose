package com.vaibhav.presentation.common.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun ConfirmExitDialog(
    onDismissRequest: () -> Unit,
    onPositiveClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            dismissOnBackPress = false
        ),
        confirmButton = {
            OutlinedButton(
                onClick = { onPositiveClick() },
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
                onClick = { onDismissRequest() },
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