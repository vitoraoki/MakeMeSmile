package com.programming.study.makemesmile

import androidx.compose.ui.window.ComposeUIViewController
import com.programming.study.makemesmile.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}