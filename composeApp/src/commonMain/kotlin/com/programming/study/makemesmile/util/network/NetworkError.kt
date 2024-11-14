package com.programming.study.makemesmile.util.network

enum class NetworkError {
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,
    NOT_FOUND,
    INTERNAL_SERVER_ERROR,
    SERVICE_UNAVAILABLE,
    TOO_MANY_REQUESTS,
    TIMEOUT,
    SERIALIZATION,
    NO_INTERNET,
    UNKNOWN;
}