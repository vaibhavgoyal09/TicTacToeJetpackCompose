package com.vaibhav.di

import com.vaibhav.presentation.online_mode.ChooseUserNameViewModel
import com.vaibhav.presentation.offline_mode.EnterUserNameViewModel
import com.vaibhav.presentation.select_room.SelectRoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        EnterUserNameViewModel()
    }

    viewModel {
        SelectRoomViewModel()
    }

    viewModel {
        ChooseUserNameViewModel()
    }
}