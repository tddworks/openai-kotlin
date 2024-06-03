package com.tddworks.common.network.api.ktor.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.exception.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.reflect.*

/**
 * Default implementation of [HttpRequester].
 * @property httpClient The HttpClient to use for performing HTTP requests.
 */
class DefaultHttpRequester(private val httpClient: HttpClient) : HttpRequester {

    override suspend fun <T : Any> performRequest(
        info: TypeInfo,
        builder: HttpRequestBuilder.() -> Unit
    ): T {
        try {
            val response = httpClient.request(builder)
            return when (response.status) {
                HttpStatusCode.OK -> response.body(info)
                else -> throw openAIAPIException(ClientRequestException(response, ""))
            }
        } catch (e: Throwable) {
            throw e
        }
    }

    override suspend fun <T : Any> streamRequest(
        info: TypeInfo,
        builder: HttpRequestBuilder.() -> Unit,
        block: suspend (response: HttpResponse) -> T,
    ) {
        try {
            HttpStatement(
                builder = HttpRequestBuilder().apply(builder),
                client = httpClient
            ).execute {
                when (it.status) {
                    HttpStatusCode.OK -> block(it)
                    else -> throw openAIAPIException(ClientRequestException(it, ""))
                }
            }
        } catch (t: Throwable) {
            throw t
        }
    }
}

fun HttpRequester.Companion.default(httpClient: HttpClient): HttpRequester {
    return DefaultHttpRequester(httpClient)
}

/**
 * Converts a [ClientRequestException] into a corresponding [OpenAIAPIException] based on the HTTP status code.
 * This function helps in handling specific API errors and categorizing them into appropriate exception classes.
 */
private suspend fun openAIAPIException(exception: ClientRequestException): OpenAIAPIException {
    val response = exception.response
    val status = response.status.value
    val error = response.body<OpenAIError>()
    return when (status) {
        429 -> RateLimitException(status, error, exception)
        400, 404, 415 -> InvalidRequestException(status, error, exception)
        401 -> AuthenticationException(status, error, exception)
        403 -> PermissionException(status, error, exception)
        else -> UnknownAPIException(status, error, exception)
    }
}

