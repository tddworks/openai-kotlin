package com.tddworks.gemini.api.textGeneration.api.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.gemini.api.textGeneration.api.GenerateContentRequest
import com.tddworks.gemini.api.textGeneration.api.GenerateContentResponse
import com.tddworks.gemini.api.textGeneration.api.TextGeneration
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

class DefaultTextGenerationApi(
    private val requester: HttpRequester
) : TextGeneration {
    override suspend fun generateContent(request: GenerateContentRequest): GenerateContentResponse {
        return requester.performRequest<GenerateContentResponse> {
            method = HttpMethod.Post
            url(path = request.toRequestUrl())
            setBody(request)
            contentType(ContentType.Application.Json)
        }
    }

    override fun streamGenerateContent(request: GenerateContentRequest): Flow<GenerateContentResponse> {
        TODO("Not yet implemented")
    }

    companion object {
        const val GEMINI_API_PATH = "/v1beta/models"
    }
}