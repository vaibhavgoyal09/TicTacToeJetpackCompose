package com.vaibhav.core.models.ws

import com.vaibhav.util.Constants.TYPE_GAME_ERROR

data class GameError(
    val errorType: Int
): BaseModel(TYPE_GAME_ERROR) {

    companion object {
        const val TYPE_ROOM_NOT_FOUND = 0
    }
}
