package com.tddworks.anthropic.api.messages.api.internal

import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 * Anthropic Messages API - https://docs.anthropic.com/claude/reference/messages_post
 */
class DefaultMessagesApi(
    private val anthropicConfig: AnthropicConfig = AnthropicConfig(),
    private val requester: HttpRequester,
    private val jsonLenient: Json = JsonLenient,
) : Messages {

    override fun stream(request: StreamMessageRequest): Flow<StreamMessageResponse> {
        return requester.streamRequest<StreamMessageResponse> {
            method = HttpMethod.Post
            url(path = MESSAGE_API_PATH)
            setBody(request.asStreamRequest(jsonLenient))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
                append("anthropic-version", anthropicConfig.anthropicVersion())
                append("x-api-key", anthropicConfig.apiKey())
            }
        }
    }

    /**
     * Create a message.
     * @param request Send a structured list of input messages with text and/or image content, and the model will generate the next message in the conversation.
     * @return The chat completion.
     */
    override suspend fun create(request: CreateMessageRequest): CreateMessageResponse {
        return requester.performRequest<CreateMessageResponse> {
            method = HttpMethod.Post
            url(path = MESSAGE_API_PATH)
            setBody(request)
            contentType(ContentType.Application.Json)
            // anthropic API uses API key and
            headers {
                append("anthropic-version", anthropicConfig.anthropicVersion())
                append("x-api-key", anthropicConfig.apiKey())
            }
        }
    }

    companion object {
        const val MESSAGE_API_PATH = "/v1/messages"
    }

}
