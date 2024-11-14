package com.programming.study.makemesmile.util.network

import com.programming.study.makemesmile.util.network.exceptions.HttpException
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class CustomHttpClient(private val engine: HttpClientEngine) {
    private val baseUrl = "https://dog.ceo/"

    fun build(): HttpClient = HttpClient(engine = engine) {
        configureJsonContentNegotiation()
        configureDefaultRequest()
        validateResponse()
    }

    private fun HttpClientConfig<*>.configureJsonContentNegotiation() {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                }
            )
        }
    }

    private fun HttpClientConfig<*>.configureDefaultRequest() {
        defaultRequest {
            url(baseUrl)
        }
    }

    private fun HttpClientConfig<*>.validateResponse() {
        expectSuccess = true
        HttpResponseValidator {
            validateResponse { response ->
                if (response.status.value >= HttpStatusCode.MultipleChoices.value) {
                    val networkError = when(response.status) {
                        HttpStatusCode.BadRequest -> NetworkError.BAD_REQUEST
                        HttpStatusCode.Unauthorized -> NetworkError.UNAUTHORIZED
                        HttpStatusCode.Forbidden -> NetworkError.FORBIDDEN
                        HttpStatusCode.NotFound -> NetworkError.NOT_FOUND
                        HttpStatusCode.InternalServerError -> NetworkError.INTERNAL_SERVER_ERROR
                        HttpStatusCode.ServiceUnavailable -> NetworkError.SERVICE_UNAVAILABLE
                        HttpStatusCode.TooManyRequests -> NetworkError.TOO_MANY_REQUESTS
                        else -> NetworkError.UNKNOWN
                    }

                    throw HttpException(
                        error = networkError,
                        message = response.bodyAsText()
                    )
                }
            }
        }
    }
}
