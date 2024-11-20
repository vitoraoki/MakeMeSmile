package com.programming.study.makemesmile.domain.usecase

import com.programming.study.makemesmile.data.repository.MakeMeSmileRepository
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

class GetRandomDogImageTest {

    private val randomDogImage = RandomDogImage(
        message = "message",
        status = "status"
    )
    private val error = Result.Error(NetworkError.UNKNOWN)

    private val repository: MakeMeSmileRepository = mock {
        everySuspend { getRandomDogImage() } returns error
    }
    private val useCase = GetRandomDogImage(repository)

    @Test
    fun invoke_verifyGetRandomDogImageCall() = runTest {
        useCase()
        verifySuspend(VerifyMode.exactly(1)) {
            repository.getRandomDogImage()
        }
    }

    @Test
    fun invoke_assertGetRandomDogImageResultSuccess() = runTest {
        val expected = Result.Success(randomDogImage)
        everySuspend { repository.getRandomDogImage() } returns expected

        val actual = useCase()

        assertEquals(expected, actual)
    }

    @Test
    fun invoke_assertGetRandomDogImageResultError() = runTest {
        val expected = Result.Error(NetworkError.UNKNOWN)

        val actual = useCase()

        assertEquals(expected, actual)
    }
}
