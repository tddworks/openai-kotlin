package com.tddworks.anthropic.api.messages.api.internal

import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.CreateMessageRequest
import com.tddworks.anthropic.api.messages.api.CreateMessageResponse
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.StreamMessageResponse
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow

/**
 * Anthropic Messages API - https://docs.anthropic.com/claude/reference/messages_post
 * All requests to the Anthropic API must include an x-api-key header with your API key
 */
class DefaultMessagesApi(
    private val anthropicConfig: AnthropicConfig = AnthropicConfig(),
    private val requester: HttpRequester,
) : Messages {

    override fun stream(request: CreateMessageRequest): Flow<StreamMessageResponse> {
        return requester.streamRequest<StreamMessageResponse> {
            method = HttpMethod.Post
            url(path = MESSAGE_API_PATH)
            setBody(request.copy(stream = true))
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
