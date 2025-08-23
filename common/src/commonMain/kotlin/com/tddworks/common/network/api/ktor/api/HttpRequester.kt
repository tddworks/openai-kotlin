package com.tddworks.common.network.api.ktor.api

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.reflect.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Interface for performing HTTP requests. */
interface HttpRequester {
    suspend fun <T : Any> performRequest(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T

    /**
     * Perform an HTTP request and get a result.
     *
     * Note: [HttpResponse] instance shouldn't be passed outside of [block].
     */
    suspend fun <T : Any> streamRequest(
        builder: HttpRequestBuilder.() -> Unit,
        block: suspend (response: HttpResponse) -> T,
    )

    companion object
}

/**
 * Perform an HTTP request and retrieve a result.
 *
 * @param builder The HttpRequestBuilder that contains the HTTP request details.
 * @return The result of the HTTP request.
 */
suspend inline fun <reified T> HttpRequester.performRequest(
    noinline builder: HttpRequestBuilder.() -> Unit
): T {
    return performRequest(typeInfo<T>(), builder)
}

/** Perform an HTTP request and get a result */
inline fun <reified T : Any> HttpRequester.streamRequest(
    noinline builder: HttpRequestBuilder.() -> Unit
): Flow<T> {
    return flow { streamRequest(builder) { response -> streamEventsFrom(response) } }
}
