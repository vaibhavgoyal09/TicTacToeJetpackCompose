package com.vaibhav.di

import com.vaibhav.presentation.enter_username.EnterUserNameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        EnterUserNameViewModel()
    }
}