package com.vaibhav.di

import com.vaibhav.presentation.online_mode.username.ChooseUserNameViewModel
import com.vaibhav.presentation.offline_mode.username.EnterPlayerNamesViewModel
import com.vaibhav.presentation.online_mode.select_room.SelectRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        EnterPlayerNamesViewModel()
    }

    viewModel {
        SelectRoomViewModel()
    }

    viewModel {
        ChooseUserNameViewModel()
    }
}