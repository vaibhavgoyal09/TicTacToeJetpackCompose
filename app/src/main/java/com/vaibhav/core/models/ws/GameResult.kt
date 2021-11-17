package com.vaibhav.core.models.ws

import com.vaibhav.util.Constants.TYPE_GAME_RESULT

data class GameResult(
    val resultType: Int
): BaseModel(TYPE_GAME_RESULT) {
    companion object {
        const val TYPE_PLAYER_WON = 0
        const val TYPE_PLAYER_LOST = 1
        const val TYPE_MATCH_DRAW = 2
    }
}
