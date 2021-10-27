package com.vaibhav.di

import com.vaibhav.presentation.username.choose_username.ChooseUserNameViewModel
import com.vaibhav.presentation.username.enter_username.EnterUserNameViewModel
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