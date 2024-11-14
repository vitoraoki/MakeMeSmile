package com.programming.study.makemesmile.data.repository

import com.programming.study.makemesmile.data.datasource.MakeMeSmileDataSource
import com.programming.study.makemesmile.domain.model.RandomDogImage
import com.programming.study.makemesmile.util.Result
import com.programming.study.makemesmile.util.network.NetworkError

interface MakeMeSmileRepository {
    suspend fun getRandomDogImage(): Result<RandomDogImage, NetworkError>
}

class MakeMeSmileDefaultRepository(
    private val dataSource: MakeMeSmileDataSource
): MakeMeSmileRepository {

    override suspend fun getRandomDogImage(): Result<RandomDogImage, NetworkError> {
        val response = dataSource.getRandomDogImage()
        return response.mapSuccess {
            RandomDogImage(message = it.message, status = it.status)
        }
    }
}
