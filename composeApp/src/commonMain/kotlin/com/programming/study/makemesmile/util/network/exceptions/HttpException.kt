package com.programming.study.makemesmile.util.network.exceptions

import com.programming.study.makemesmile.util.network.NetworkError

data class HttpException(
    val error: NetworkError,
    override val message: String
): Exception()