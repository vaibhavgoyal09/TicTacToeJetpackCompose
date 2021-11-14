package com.vaibhav.presentation.offline_mode.game

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OfflineGameViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _player1ScoreState = mutableStateOf(0)
    val player1ScoreState: State<Int> = _player1ScoreState

    private val _player2ScoreState = mutableStateOf(0)
    val player2ScoreState: State<Int> = _player2ScoreState

    private var movesCounter = 0

    private val gameState = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)

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

    fun onEvent(event: OfflineGameEvent) {

    }
}