package com.programming.study.makemesmile.data.datasource

import com.programming.study.makemesmile.data.model.RandomDogImageResponse
import com.programming.study.makemesmile.util.Result
import com.programming.study.makemesmile.util.network.CustomHttpClient
import com.programming.study.makemesmile.util.network.NetworkError
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

private const val RANDOM_IMAGE_PATH = "/api/breeds/image/random"

class MakeMeSmileRemoteDataSourceTest {

    private val randomDogImageResponse = RandomDogImageResponse(
        message = "message",
        status = "status"
    )

    private lateinit var dataSource: MakeMeSmileRemoteDataSource

    @Test
    fun getRandomDogImage_withEmptyResponse_assertSerializationError() = runTest {
        dataSource = buildDataSource(content = "")

        val actual = dataSource.getRandomDogImage()

        val expected = Result.Error(NetworkError.SERIALIZATION)

        assertEquals(expected, actual)
    }

    @Test
    fun getRandomDogImage_withWrongJsonResponse_assertSerializationError() = runTest {
        dataSource = buildDataSource(content = "{\"message\": 1}")

        val actual = dataSource.getRandomDogImage()

        val expected = Result.Error(NetworkError.SERIALIZATION)

        assertEquals(expected, actual)
    }

    @Test
    fun getRandomDogImage_withHttpException_assertNetworkError() = runTest {
        dataSource = buildDataSource(status = HttpStatusCode.NotFound)

        val actual = dataSource.getRandomDogImage()

        val expected = Result.Error(NetworkError.NOT_FOUND)

        assertEquals(expected, actual)
    }

    @Test
    fun getRandomDogImage_withDefaultException_assertUnknownError() = runTest {
        dataSource = buildDataSourceWithDefaultException()

        val actual = dataSource.getRandomDogImage()

        val expected = Result.Error(NetworkError.UNKNOWN)

        assertEquals(expected, actual)
    }

    @Test
    fun getRandomDogImage_withSuccessfulResponse_assertRandomDogImageResponse() = runTest {
        dataSource = buildDataSource()

        val actual = dataSource.getRandomDogImage()

        val expected = Result.Success(randomDogImageResponse)

        assertEquals(expected, actual)
    }

    private fun buildDataSource(
        content: String = Json.encodeToString(randomDogImageResponse),
        status: HttpStatusCode = HttpStatusCode.OK
    ) = MakeMeSmileRemoteDataSource(buildCustomHttpClient(content, status))

    private fun buildCustomHttpClient(
        content: String = Json.encodeToString(randomDogImageResponse),
        status: HttpStatusCode = HttpStatusCode.OK
    ): HttpClient = CustomHttpClient(
        engine = MockEngine { response ->
            if (response.url.encodedPath == RANDOM_IMAGE_PATH) {
                respond(
                    content = content,
                    status = status,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                error("")
            }
        }
    ).build()

    private fun buildDataSourceWithDefaultException() = MakeMeSmileRemoteDataSource(
        client = CustomHttpClient(
            engine = MockEngine {
                throw Exception()
            }
        ).build()
    )
}
