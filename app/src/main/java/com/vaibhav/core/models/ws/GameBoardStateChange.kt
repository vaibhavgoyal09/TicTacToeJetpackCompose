package com.vaibhav.core.models.ws

import com.vaibhav.util.Constants.TYPE_GAME_BOARD_STATE_CHANGED

data class GameBoardStateChange(
    val state: List<Int>
): BaseModel(TYPE_GAME_BOARD_STATE_CHANGED)
