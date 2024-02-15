package com.tddworks.openai.api.internal.network.ktor

import com.tddworks.openai.api.internal.network.ktor.exception.*
import com.tddworks.openai.api.internal.network.ktor.internal.createHttpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.CancellationException

/**
 * Default implementation of [HttpRequester].
 * @property httpClient The HttpClient to use for performing HTTP requests.
 */
internal class DefaultHttpRequester(private val httpClient: HttpClient) : HttpRequester {

    override suspend fun <T : Any> performRequest(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T {
        try {
            val response = httpClient.request(builder)
            return response.body(info)
        } catch (e: Exception) {
            throw handleException(e)
        }
    }

    override suspend fun <T : Any> streamRequest(
        info: TypeInfo,
        builder: HttpRequestBuilder.() -> Unit,
        block: suspend (response: HttpResponse) -> T,
    ) {
        try {
            HttpStatement(builder = HttpRequestBuilder().apply(builder), client = httpClient).execute(block)
        } catch (e: Exception) {
            throw handleException(e)
        }
    }
}

fun HttpRequester.Companion.default(httpClient: HttpClient): HttpRequester {
    return DefaultHttpRequester(httpClient)
}

fun HttpRequester.Companion.default(
    url: String,
    token: String,
    engine: HttpClientEngine,
): HttpRequester {
    return DefaultHttpRequester(
        createHttpClient(
            url = url,
            token = token,
            engine = engine
        )
    )
}


/**
 * Handles various exceptions that can occur during an API request and converts them into appropriate
 * [OpenAIException] instances.
 */
private suspend fun handleException(e: Throwable) = when (e) {
    is CancellationException -> e // propagate coroutine cancellation
    is ClientRequestException -> openAIAPIException(e)
    is ServerResponseException -> OpenAIServerException(e)
    is HttpRequestTimeoutException, is SocketTimeoutException, is ConnectTimeoutException -> OpenAITimeoutException(e)
    is IOException -> GenericIOException(e)
    else -> OpenAIHttpException(e)
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

