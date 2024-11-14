package com.programming.study.makemesmile.di

import com.programming.study.makemesmile.TestViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::TestViewModel)
}
