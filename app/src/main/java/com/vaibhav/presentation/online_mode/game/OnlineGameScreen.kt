package com.vaibhav.presentation.online_mode.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaibhav.R
import com.vaibhav.presentation.common.components.ConfirmExitDialog
import com.vaibhav.presentation.common.components.GameBoard
import com.vaibhav.presentation.common.components.StandardScoreboard
import com.vaibhav.presentation.common.components.StandardSettingsIcon
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OnlineGameScreen(
    viewModel: OnlineGameViewModel = hiltViewModel(),
    isBackHandleEnabled: Boolean = false,
    scaffoldState: ScaffoldState,
    onNavigateUp: () -> Unit
) {

    val boardState = viewModel.boardState.value
    val player1Symbol = viewModel.player1SymbolState.value
    val player2Symbol = viewModel.player2SymbolState.value

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is OnlineGameUiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is OnlineGameUiEvent.ShowWinDialog -> {
                    scaffoldState.snackbarHostState.showSnackbar("You win")
                }
                is OnlineGameUiEvent.ShowLostDialog -> {
                    scaffoldState.snackbarHostState.showSnackbar("You lost")
                }
                is OnlineGameUiEvent.ShowMatchDrawDialog -> {
                    scaffoldState.snackbarHostState.showSnackbar("Match draw")
                }
                is OnlineGameUiEvent.NavigateUp -> {
                    onNavigateUp()
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
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = player1Name,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                StandardPlayerSymbol(
                    symbol = player1Symbol,
                    contentDescription = "Player1 Symbol"
                )
            }

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

            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = player2Name,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onBackground
                )

                StandardPlayerSymbol(
                    symbol = player2Symbol,
                    contentDescription = "Player2 Symbol"
                )
            }
        }

        GameBoard(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            gameState = boardState
        ) {
            viewModel.onEvent(OnlineGameEvent.GameMove(it))
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

@Composable
fun StandardPlayerSymbol(
    modifier: Modifier = Modifier,
    symbol: Int,
    contentDescription: String? = null
) {
    when (symbol) {
        1 -> {
            Image(
                painter = painterResource(id = R.drawable.x),
                contentDescription = contentDescription,
                modifier = modifier.size(30.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}