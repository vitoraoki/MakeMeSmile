package com.programming.study.makemesmile.util

sealed class Result<out S, out E> {
    data class Success<out S>(val value: S): Result<S, Nothing>()
    data class Error<out E>(val error: E): Result<Nothing, E>()

    inline fun onSuccess(action: (value: S) -> Unit): Result<S, E> {
        if (this is Success) {
            action(this.value)
        }
        return this
    }

    inline fun onError(action: (error: E) -> Unit): Result<S, E> {
        if (this is Error) {
            action(error)
        }
        return this
    }

    inline fun getValueOrNull(): S? {
        return when(this) {
            is Error -> null
            is Success -> value
        }
    }

    inline fun <R> mapSuccess(map: (S) -> R): Result<R, E> {
        return when(this) {
            is Error -> Error(error)
            is Success -> Success(map(value))
        }
    }

    inline fun <R> mapError(map: (E) -> R): Result<S, R> {
        return when(this) {
            is Error -> Error(map(error))
            is Success -> Success(value)
        }
    }
}
