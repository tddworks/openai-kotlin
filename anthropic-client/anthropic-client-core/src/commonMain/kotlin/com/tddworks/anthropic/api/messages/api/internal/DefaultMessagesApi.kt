package com.tddworks.anthropic.api.messages.api.internal

import com.tddworks.common.network.api.StreamChatResponse
import com.tddworks.common.network.api.StreamableRequest
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import com.tddworks.openllm.api.ChatApi
import com.tddworks.openllm.api.ChatRequest
import com.tddworks.openllm.api.ChatResponse
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

/**
 *  * Anthropic Messages API -https://docs.anthropic.com/claude/reference/messages_post
 */
class DefaultMessagesApi(
    private val requester: HttpRequester,
    private val jsonLenient: Json = JsonLenient,
) : ChatApi {

    override fun chat(request: StreamableRequest): Flow<StreamChatResponse> {
        return requester.streamRequest<StreamChatResponse> {
            method = HttpMethod.Post
            url(path = CHAT_COMPLETIONS_PATH)
            setBody(request.asStreamRequest(jsonLenient))
            contentType(ContentType.Application.Json)
            accept(ContentType.Text.EventStream)
            headers {
                append(HttpHeaders.CacheControl, "no-cache")
                append(HttpHeaders.Connection, "keep-alive")
            }
        }
    }

    /**
     * Create a message.
     * @param request Send a structured list of input messages with text and/or image content, and the model will generate the next message in the conversation.
     * @return The chat completion.
     */
    override suspend fun chat(request: ChatRequest): ChatResponse {
        return requester.performRequest<ChatResponse> {
            method = HttpMethod.Post
            url(path = CHAT_COMPLETIONS_PATH)
            setBody(request)
            contentType(ContentType.Application.Json)
        }
    }

    companion object {
        const val CHAT_COMPLETIONS_PATH = "/v1/messages"
    }

}
