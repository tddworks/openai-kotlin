package com.tddworks.openai.api.chat

import kotlinx.coroutines.flow.Flow

/**
 * Chat API - https://platform.openai.com/docs/api-reference/chat
 * Given a list of messages comprising a conversation, the model will return a response.
 * Related guide: Chat Completions
 */
interface Chat {
    /**
     * Create a chat completion.
     * @param request The request to create a chat completion.
     * @return The chat completion.
     */
    suspend fun completions(request: ChatCompletionRequest): ChatCompletion


    /**
     * Stream a chat completion.
     * @param request The request to stream a chat completion.
     * @return The chat completion chunks as a stream
     */
    fun streamCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk>

    companion object {
        const val CHAT_COMPLETIONS_PATH = "/v1/chat/completions"
    }
}
