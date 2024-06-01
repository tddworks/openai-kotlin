package com.tddworks.openai.api.legacy.completions.api.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.api.legacy.completions.api.Completions
import io.ktor.client.request.*
import io.ktor.http.*

class DefaultCompletionsApi(
    private val requester: HttpRequester,
) : Completions {
    override suspend fun completions(request: CompletionRequest): Completion {
        return requester.performRequest<Completion> {
            method = HttpMethod.Post
            url(path = COMPLETIONS_PATH)
            setBody(request)
            contentType(ContentType.Application.Json)
        }
    }

    companion object {
        const val COMPLETIONS_PATH = "/v1/completions"
    }
}