package com.programming.study.makemesmile.data.repository

import com.programming.study.makemesmile.data.datasource.MakeMeSmileDataSource
import com.programming.study.makemesmile.data.model.RandomDogImageResponse
import com.programming.study.makemesmile.domain.model.RandomDogImage
import com.programming.study.makemesmile.util.Result
import com.programming.study.makemesmile.util.network.NetworkError
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MakeMeSmileDefaultRepositoryTest {

    private val error = Result.Error(NetworkError.UNKNOWN)

    private val dataSource: MakeMeSmileDataSource = mock {
        everySuspend { getRandomDogImage() } returns error
    }
    private val repository = MakeMeSmileDefaultRepository(dataSource)

    @Test
    fun getRandomDogImage_verifyGetRandomDogImageCall() = runTest {
        repository.getRandomDogImage()

        verifySuspend(VerifyMode.exactly(1)) {
            dataSource.getRandomDogImage()
        }
    }

    @Test
    fun getRandomDogImage_withDataSourceResponseError_assertError() = runTest {
        val actual = repository.getRandomDogImage()

        val expected = Result.Error(NetworkError.UNKNOWN)

        assertEquals(expected, actual)
    }

    @Test
    fun getRandomDogImage_withDataSourceResponseSuccess_assertRandomDogImage() = runTest {
        val randomDogImageResponse = RandomDogImageResponse(
            message = "message",
            status = "status"
        )

        everySuspend {
            dataSource.getRandomDogImage()
        } returns Result.Success(randomDogImageResponse)

        val actual = repository.getRandomDogImage()

        val randomDogImage = RandomDogImage(
            message = randomDogImageResponse.message,
            status = randomDogImageResponse.status
        )
        val expected = Result.Success(randomDogImage)

        assertEquals(expected, actual)
    }
}
