package com.tddworks.openai.api.chat.api

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * Chat API - https://platform.openai.com/docs/api-reference/chat
 * Given a list of messages comprising a conversation, the model will return a response.
 * Related guide: Chat Completions
 */
@OptIn(ExperimentalSerializationApi::class)
interface Chat {
    /**
     * Create a chat completion.
     * @param request The request to create a chat completion.
     * @return The chat completion.
     */
    suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion


    /**
     * Stream a chat completion.
     * @param request The request to stream a chat completion.
     * @return The chat completion chunks as a stream
     */
    fun streamChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk>

    companion object {
        const val CHAT_COMPLETIONS_PATH = "/v1/chat/completions"
    }
}
