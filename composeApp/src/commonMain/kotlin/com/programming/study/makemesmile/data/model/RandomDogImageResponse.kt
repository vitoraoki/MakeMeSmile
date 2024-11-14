package com.programming.study.makemesmile.data.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomDogImageResponse(
    val message: String,
    val status: String
)
