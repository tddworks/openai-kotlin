package com.tddworks.openai.api.internal.network.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.util.reflect.*

/**
 * Default implementation of [HttpRequester].
 * @property httpClient The HttpClient to use for performing HTTP requests.
 */
internal class DefaultHttpRequester(private val httpClient: HttpClient) : HttpRequester {

    override suspend fun <T : Any> performRequest(info: TypeInfo, builder: HttpRequestBuilder.() -> Unit): T {
        val response = httpClient.request(builder)
        return response.body(info)
    }

    override suspend fun <T : Any> streamRequest(
        info: TypeInfo,
        builder: HttpRequestBuilder.() -> Unit,
        block: suspend (response: HttpResponse) -> T,
    ) {
        HttpStatement(builder = HttpRequestBuilder().apply(builder), client = httpClient).execute(block)
    }
}

fun HttpRequester.Companion.default(httpClient: HttpClient): HttpRequester {
    return DefaultHttpRequester(httpClient)
}
