package com.tddworks.openai.gateway.api

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.Model
import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.ExperimentalSerializationApi
import com.tddworks.openai.api.chat.api.ChatCompletion as OpenAIChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk as OpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.Model as OpenAIModel

@OptIn(ExperimentalSerializationApi::class)
class AnthropicOpenAIProvider(private val client: Anthropic) : OpenAIProvider {
    /**
     * Check if the given OpenAIModel is supported by the available models.
     * @param model The OpenAIModel to check for support.
     * @return true if the model is supported, false otherwise.
     */
    override fun supports(model: OpenAIModel): Boolean {
        return Model.availableModels.any { it.value == model.value }
    }

    /**
     * Override function to fetch completions from OpenAI API based on the given ChatCompletionRequest
     * @param request the ChatCompletionRequest object containing information needed to generate completions
     * @return OpenAIChatCompletion object containing completions generated from the OpenAI API
     */
    override suspend fun completions(request: ChatCompletionRequest): OpenAIChatCompletion {
        val anthropicRequest = request.toAnthropicRequest()
        return client.create(anthropicRequest).toOpenAIChatCompletion()
    }

    /**
     * A function that streams completions for chat based on the given ChatCompletionRequest
     *
     * @param request The ChatCompletionRequest containing the request details
     * @return A Flow of OpenAIChatCompletionChunk objects representing the completions
     */
    override fun streamCompletions(request: ChatCompletionRequest): Flow<OpenAIChatCompletionChunk> {
        return client.stream(request.toAnthropicRequest() as StreamMessageRequest)
            .filter { it !is ContentBlockStop && it !is Ping }
            .transform {
                emit(it.toOpenAIChatCompletionChunk(request.model.value))
            }
    }
}