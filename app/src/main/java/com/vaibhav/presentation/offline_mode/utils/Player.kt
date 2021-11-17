package com.vaibhav.presentation.offline_mode.utils

data class Player(
    val userName: String,
    val symbol: Int = NO_SYMBOL
) {
    companion object {
        const val NO_SYMBOL = 0
        const val SYMBOL_X = 1
        const val SYMBOL_O = 2
    }
}
