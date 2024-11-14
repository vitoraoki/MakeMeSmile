package com.programming.study.makemesmile.data.datasource

import com.programming.study.makemesmile.data.model.RandomDogImageResponse
import com.programming.study.makemesmile.util.network.NetworkError
import com.programming.study.makemesmile.util.Result

interface MakeMeSmileDataSource {
    suspend fun getRandomDogImage(): Result<RandomDogImageResponse, NetworkError>
}
