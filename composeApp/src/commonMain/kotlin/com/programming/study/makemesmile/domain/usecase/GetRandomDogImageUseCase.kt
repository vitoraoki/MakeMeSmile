package com.programming.study.makemesmile.domain.usecase

import com.programming.study.makemesmile.data.repository.MakeMeSmileRepository
import com.programming.study.makemesmile.domain.model.RandomDogImage
import com.programming.study.makemesmile.util.Result
import com.programming.study.makemesmile.util.network.NetworkError

interface GetRandomDogImageUseCase {
    suspend operator fun invoke(): Result<RandomDogImage, NetworkError>
}

class GetRandomDogImage(
    private val repository: MakeMeSmileRepository
) : GetRandomDogImageUseCase {

    override suspend fun invoke(): Result<RandomDogImage, NetworkError> {
        return repository.getRandomDogImage()
    }
}
