package com.tddworks.openai.api.chat

import com.snacks.openai.api.chat.ChatCompletionChunk
import kotlinx.coroutines.flow.Flow

/**
 * Chat API - https://platform.openai.com/docs/api-reference/chat
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
