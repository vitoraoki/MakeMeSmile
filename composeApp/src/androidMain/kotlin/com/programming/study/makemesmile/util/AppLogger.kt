package com.programming.study.makemesmile.util

import android.util.Log

actual object AppLogger {
    actual fun e(tag: String, message: String, throwable: Throwable?) {
        Log.e(tag, message)
    }

    actual fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    actual fun i(tag: String, message: String) {
        Log.i(tag, message)
    }
}
