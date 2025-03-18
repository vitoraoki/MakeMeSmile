package com.programming.study.makemesmile.di

import com.programming.study.makemesmile.presentation.viewmodel.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::MainViewModel)
}
