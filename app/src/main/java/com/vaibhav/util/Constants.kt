package com.vaibhav.util

object Constants {

    const val MAX_USERNAME_CHAR_COUNT = 15
    const val MIN_USERNAME_CHAR_COUNT = 4
    const val MAX_ROOM_NAME_CHAR_COUNT = 25

    const val TIC_TAC_TOE_SOCKET_API_URL = "wss://tic-tac-toe-vaibhav.herokuapp.com/v1/game"
    const val MIN_ROOM_NAME_CHAR_COUNT = 4

    const val TYPE_JOIN_ROOM = "TYPE_JOIN_ROOM"
    const val TYPE_GAME_ERROR = "TYPE_GAME_ERROR"
    const val TYPE_PHASE_CHANGE = "TYPE_PHASE_CHANGE"
    const val TYPE_DISCONNECT_REQUEST = "TYPE_DISCONNECT_REQUEST"
    const val TYPE_ANNOUNCEMENT = "TYPE_ANNOUNCEMENT"
    const val TYPE_START_GAME = "TYPE_START_GAME"
    const val TYPE_GAME_MOVE = "TYPE_GAME_MOVE"
}