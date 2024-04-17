package com.tddworks.ollama.api.chat.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.chat.OllamaChatRequest
import com.tddworks.ollama.api.chat.OllamaChatResponse
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class DefaultOllamaChatApi(
    private val requester: HttpRequester,
    private val jsonLenient: Json = JsonLenient,
) : OllamaChat {
    override fun stream(request: OllamaChatRequest): Flow<OllamaChatResponse> {
        return requester.streamRequest<OllamaChatResponse> {
            method = HttpMethod.Post
            url(path = CHAT_API_PATH)
            setBody(request.asStreamRequest(jsonLenient))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

    override suspend fun request(request: OllamaChatRequest): OllamaChatResponse {
        return requester.performRequest {
            method = HttpMethod.Post
            url(path = CHAT_API_PATH)
            setBody(request.asNonStreaming(jsonLenient))
            contentType(ContentType.Application.Json)
        }
    }

    companion object {
        const val CHAT_API_PATH = "/api/chat"
    }
}

