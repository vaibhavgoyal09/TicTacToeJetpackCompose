package com.vaibhav.core.models.ws

import com.vaibhav.core.models.Room
import com.vaibhav.util.Constants.TYPE_PHASE_CHANGE

data class GamePhaseChange(
    val gamePhase: Room.GamePhase?,
    val time: Long
): BaseModel(TYPE_PHASE_CHANGE)
