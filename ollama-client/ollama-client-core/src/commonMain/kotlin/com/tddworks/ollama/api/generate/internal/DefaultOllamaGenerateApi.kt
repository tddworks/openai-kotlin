package com.tddworks.ollama.api.generate.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import com.tddworks.ollama.api.generate.OllamaGenerate
import com.tddworks.ollama.api.generate.OllamaGenerateRequest
import com.tddworks.ollama.api.generate.OllamaGenerateResponse
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

/** Default implementation of Ollama generate */
class DefaultOllamaGenerateApi(private val requester: HttpRequester) : OllamaGenerate {
    override fun stream(request: OllamaGenerateRequest): Flow<OllamaGenerateResponse> {
        return requester.streamRequest<OllamaGenerateResponse> {
            method = HttpMethod.Post
            url(path = GENERATE_API_PATH)
            setBody(request.copy(stream = true))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

    override suspend fun request(request: OllamaGenerateRequest): OllamaGenerateResponse {
        return requester.performRequest {
            method = HttpMethod.Post
            url(path = GENERATE_API_PATH)
            setBody(request)
            contentType(ContentType.Application.Json)
        }
    }

    companion object {
        const val GENERATE_API_PATH = "/api/generate"
    }
}
