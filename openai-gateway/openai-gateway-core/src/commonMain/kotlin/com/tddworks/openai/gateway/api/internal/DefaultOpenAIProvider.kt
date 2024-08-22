@file:OptIn(ExperimentalSerializationApi::class)

package com.tddworks.openai.gateway.api.internal

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.chat.api.OpenAIModel.Companion.availableModels
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class DefaultOpenAIProvider(
    override val id: String = "openai",
    override val name: String = "OpenAI",
    override val models: List<OpenAIModel> = availableModels,
    override val config: OpenAIProviderConfig,
    private val openAI: OpenAI = OpenAI.create(config.toOpenAIConfig()),
) : OpenAIProvider {

    override fun supports(model: OpenAIModel): Boolean {
        return models.any { it.value == model.value }
    }

    override suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion {
        return openAI.chatCompletions(request)
    }

    override fun streamChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        return openAI.streamChatCompletions(request)
    }

    override suspend fun completions(request: CompletionRequest): Completion {
        return openAI.completions(request)
    }
}

fun OpenAIProvider.Companion.openAI(
    id: String = "openai",
    config: OpenAIProviderConfig,
    models: List<OpenAIModel>,
    openAI: OpenAI = OpenAI.create(config.toOpenAIConfig())
): OpenAIProvider {
    return DefaultOpenAIProvider(
        id = id,
        config = config, models = models, openAI = openAI
    )
}
