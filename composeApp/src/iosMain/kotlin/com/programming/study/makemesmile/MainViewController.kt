package com.programming.study.makemesmile

import androidx.compose.ui.window.ComposeUIViewController
import com.programming.study.makemesmile.di.initKoin
import com.programming.study.makemesmile.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
