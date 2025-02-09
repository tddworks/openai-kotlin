package com.tddworks.openai.gateway.api.internal

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.common.network.api.ktor.api.ListResponse
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.images.api.Image
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.ExperimentalSerializationApi
import com.tddworks.openai.api.chat.api.ChatCompletion as OpenAIChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk as OpenAIChatCompletionChunk

@OptIn(ExperimentalSerializationApi::class)
class AnthropicOpenAIProvider(
    override var id: String = "anthropic",
    override var name: String = "Anthropic",
    override var models: List<OpenAIModel> = AnthropicModel.availableModels.map {
        OpenAIModel(it.value)
    },
    override var config: AnthropicOpenAIProviderConfig,

    private val client: Anthropic = Anthropic.create(
        AnthropicConfig(
            apiKey = config.apiKey,
            baseUrl = config.baseUrl,
            anthropicVersion = config.anthropicVersion
        )
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

    override suspend fun generate(request: ImageCreate): ListResponse<Image> {
        throw UnsupportedOperationException("Not supported")
    }
}

fun OpenAIProvider.Companion.anthropic(
    id: String = "anthropic",
    config: AnthropicOpenAIProviderConfig,
    models: List<OpenAIModel> = AnthropicModel.availableModels.map {
        OpenAIModel(it.value)
    },
    client: Anthropic = Anthropic.create(
        AnthropicConfig(
            apiKey = config.apiKey,
            baseUrl = config.baseUrl,
            anthropicVersion = config.anthropicVersion
        )
    )
): OpenAIProvider {
    return AnthropicOpenAIProvider(
        id = id,
        config = config,
        models = models,
        client = client
    )
}