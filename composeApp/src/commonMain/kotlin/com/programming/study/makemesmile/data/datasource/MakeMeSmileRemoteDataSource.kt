package com.programming.study.makemesmile.data.datasource

import com.programming.study.makemesmile.data.model.RandomDogImageResponse
import com.programming.study.makemesmile.util.network.NetworkError
import com.programming.study.makemesmile.util.Result
import com.programming.study.makemesmile.util.network.exceptions.HttpException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.serialization.SerializationException

private const val RANDOM_IMAGE_PATH = "api/breeds/image/random"

class MakeMeSmileRemoteDataSource(
    private val client: HttpClient
) : MakeMeSmileDataSource {

    override suspend fun getRandomDogImage(): Result<RandomDogImageResponse, NetworkError> =
        try {
            val response: RandomDogImageResponse = client.get(RANDOM_IMAGE_PATH).body()
            Result.Success(response)
        } catch (e: SerializationException) {
            Result.Error(NetworkError.SERIALIZATION)
        } catch (e: HttpException) {
            Result.Error(e.error)
        } catch (e: Exception) {
            Result.Error(NetworkError.UNKNOWN)
        }
}