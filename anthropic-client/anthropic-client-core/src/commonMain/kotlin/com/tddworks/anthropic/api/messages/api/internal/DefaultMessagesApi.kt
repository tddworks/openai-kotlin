package com.tddworks.anthropic.api.messages.api.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.openllm.api.ChatApi
import com.tddworks.openllm.api.ChatRequest
import com.tddworks.openllm.api.ChatResponse
import io.ktor.client.request.*
import io.ktor.http.*

/**
 *  * Anthropic Messages API -https://docs.anthropic.com/claude/reference/messages_post
 */
class DefaultMessagesApi(private val requester: HttpRequester) : ChatApi {
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
