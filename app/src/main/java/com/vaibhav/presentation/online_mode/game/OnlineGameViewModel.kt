package com.vaibhav.presentation.online_mode.game

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vaibhav.presentation.common.util.UiEvent
import com.vaibhav.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class OnlineGameViewModel @Inject constructor (
    private val dispatcherProvider: DispatcherProvider,
    private val savedStateHandle: SavedStateHandle,
    private val clientId: String
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


}