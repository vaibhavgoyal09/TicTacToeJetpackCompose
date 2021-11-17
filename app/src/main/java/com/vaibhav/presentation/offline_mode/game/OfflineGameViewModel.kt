package com.vaibhav.presentation.offline_mode.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.presentation.offline_mode.utils.Player
import com.vaibhav.presentation.offline_mode.utils.Player.Companion.NO_SYMBOL
import com.vaibhav.presentation.offline_mode.utils.Player.Companion.SYMBOL_O
import com.vaibhav.presentation.offline_mode.utils.Player.Companion.SYMBOL_X
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineGameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _player1ScoreState = mutableStateOf(0)
    val player1ScoreState: State<Int> = _player1ScoreState

    private val _player2ScoreState = mutableStateOf(0)
    val player2ScoreState: State<Int> = _player2ScoreState

    private val _uiEvent: MutableSharedFlow<OfflineScreenUiEvent> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    private var movesCounter = 0

    private val gameBoardPositions = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

    private val _boardState = mutableStateOf(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0))
    val boardState: State<List<Int>> = _boardState

    private var hasGameEnded = false

    private val winPositions: Array<IntArray> = arrayOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6)
    )

    private val players: MutableList<Player> = ArrayList()
    private var playerWithTurn: Player? = null

    init {
        val player1 = Player(
            savedStateHandle.get<String>("player1Name") ?: "",
            SYMBOL_X
        )
        players.add(player1)

        val player2 = Player(
            savedStateHandle.get<String>("player2Name") ?: "",
            SYMBOL_O
        )
        players.add(player2)

        playerWithTurn = player1
    }

    fun onEvent(event: OfflineGameEvent) {
        when (event) {
            is OfflineGameEvent.GameMove -> {
                viewModelScope.launch {
                    if (gameBoardPositions[event.position] != NO_SYMBOL || hasGameEnded) {
                        return@launch
                    }
                    gameBoardPositions[event.position] = playerWithTurn?.symbol!!
                    _boardState.value = ArrayList(gameBoardPositions.asList())
                    playerWithTurn = players.find { it.symbol != playerWithTurn?.symbol }
                    movesCounter++
                    emitMatchState()
                }
            }
        }
    }

    private suspend fun emitMatchState() {
        var isAnyoneWin = false
        for (winPosition in winPositions) {
            if (
                gameBoardPositions[winPosition[0]] == gameBoardPositions[winPosition[1]] &&
                gameBoardPositions[winPosition[1]] == gameBoardPositions[winPosition[2]] &&
                gameBoardPositions[winPosition[0]] != 0
            ) {
                isAnyoneWin = true
                val winnerPlayerUserName = if (gameBoardPositions[winPosition[0]] == SYMBOL_X) {
                    players.find { it.symbol == SYMBOL_X }?.userName
                } else {
                    players.find { it.symbol == SYMBOL_O }?.userName
                }
                if (winnerPlayerUserName != null) {
                    hasGameEnded = true
                    _uiEvent.emit(OfflineScreenUiEvent.ShowWinDialog(winnerPlayerUserName))
                }
            }
        }
        if (movesCounter >= 9 && !isAnyoneWin) {
            // Match draw
            hasGameEnded = true
            _uiEvent.emit(OfflineScreenUiEvent.ShowMatchDrawDialog)
        }
    }
}