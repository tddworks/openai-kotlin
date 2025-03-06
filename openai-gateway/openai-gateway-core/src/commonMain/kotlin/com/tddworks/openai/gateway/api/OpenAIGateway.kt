package com.tddworks.openai.gateway.api

import com.tddworks.common.network.api.ktor.api.ListResponse
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.images.api.Image
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import kotlinx.coroutines.flow.Flow

/**
 * Interface for connecting to the OpenAI Gateway to chat.
 */
interface OpenAIGateway {
    fun updateProvider(
        id: String,
        name: String,
        config: OpenAIProviderConfig
    )

    fun addProvider(provider: OpenAIProvider): OpenAIGateway
    fun removeProvider(id: String)
    fun getProviders(): List<OpenAIProvider>

    /**
     * Creates an image given a prompt.
     * Get images as URLs or base64-encoded JSON.
     * @param request image creation request.
     * @return list of images.
     */
    suspend fun generate(request: ImageCreate, provider: LLMProvider): ListResponse<Image>

    /**
     * Fetch a completion.
     * @param request The request to fetch a completion.
     * @param provider The provider to use for the completion.
     * @return The completion
     */
    suspend fun completions(request: CompletionRequest, provider: LLMProvider): Completion

    /**
     * Fetch a chat completion.
     * @param request The request to fetch a chat completion.
     * @param provider The provider to use for the chat completion.
     * @return The chat completion
     */
    suspend fun chatCompletions(request: ChatCompletionRequest, provider: LLMProvider): ChatCompletion


    /**
     * Stream a chat completion.
     * @param request The request to stream a chat completion.
     * @param provider The provider to use for the chat completion.
     * @return The chat completion chunks as a stream
     */
    fun streamChatCompletions(request: ChatCompletionRequest, provider: LLMProvider): Flow<ChatCompletionChunk>
}