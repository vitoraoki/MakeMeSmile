package com.programming.study.makemesmile.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(commonModule, platformModule)
    }
}