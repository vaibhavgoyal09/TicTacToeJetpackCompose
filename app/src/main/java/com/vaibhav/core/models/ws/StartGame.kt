package com.vaibhav.core.models.ws

import com.vaibhav.util.Constants.TYPE_START_GAME

data class StartGame(
    val playerWithSymbolXUserName: String,
    val playerWithSymbolOUserName: String
): BaseModel(TYPE_START_GAME)