package com.tddworks.openai.gateway.api

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.anthropic.api.internal.create
import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.internal.AnthropicOpenAIProviderConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.ExperimentalSerializationApi
import com.tddworks.openai.api.chat.api.ChatCompletion as OpenAIChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk as OpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.OpenAIModel as OpenAIModel

@OptIn(ExperimentalSerializationApi::class)
class AnthropicOpenAIProvider(
    override val name: String = "Anthropic",
    override val models: List<OpenAIModel> = AnthropicModel.availableModels.map {
        OpenAIModel(it.value)
    },
    override val config: AnthropicOpenAIProviderConfig,

    private val client: Anthropic = Anthropic.create(
        apiKey = config.apiKey(),
        apiURL = config.baseUrl(),
        anthropicVersion = config.anthropicVersion()
    )

) : OpenAIProvider {

    /**
     * Check if the given OpenAIModel is supported by the available models.
     * @param model The OpenAIModel to check for support.
     * @return true if the model is supported, false otherwise.
     */
    override fun supports(model: OpenAIModel): Boolean {
        return models.any { it.value == model.value }
    }

    /**
     * Override function to fetch completions from OpenAI API based on the given ChatCompletionRequest
     * @param request the ChatCompletionRequest object containing information needed to generate completions
     * @return OpenAIChatCompletion object containing completions generated from the OpenAI API
     */
    override suspend fun chatCompletions(request: ChatCompletionRequest): OpenAIChatCompletion {
        val anthropicRequest = request.toAnthropicRequest()
        return client.create(anthropicRequest).toOpenAIChatCompletion()
    }

    /**
     * A function that streams completions for chat based on the given ChatCompletionRequest
     *
     * @param request The ChatCompletionRequest containing the request details
     * @return A Flow of OpenAIChatCompletionChunk objects representing the completions
     */
    override fun streamChatCompletions(request: ChatCompletionRequest): Flow<OpenAIChatCompletionChunk> {
        return client.stream(
            request.toAnthropicRequest().copy(
                stream = true
            )
        ).filter { it !is ContentBlockStop && it !is Ping }.transform {
            emit(it.toOpenAIChatCompletionChunk(request.model.value))
        }
    }

    override suspend fun completions(request: CompletionRequest): Completion {
        throw UnsupportedOperationException("Not supported")
    }
}