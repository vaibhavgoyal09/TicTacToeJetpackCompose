package com.vaibhav.core.networking

object HttpRoutes {

    private const val TIC_TAC_TOE_HTTP_API_BASE_URL = "https://tic-tac-toe-vaibhav.herokuapp.com/v1/"

    const val CREATE_ROOM_ROUTE = "$TIC_TAC_TOE_HTTP_API_BASE_URL/room/create"
    const val SEARCH_ROOM_ROUTE = "$TIC_TAC_TOE_HTTP_API_BASE_URL/room/search"
    const val JOIN_ROOM_ROUTE = "$TIC_TAC_TOE_HTTP_API_BASE_URL/room/join"
}