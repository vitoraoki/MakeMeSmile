package com.programming.study.makemesmile.application

import android.app.Application
import com.programming.study.makemesmile.di.initKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}