@file:OptIn(ExperimentalSerializationApi::class)

package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class DefaultOpenAIProvider(
    config: OpenAIConfig,
    models: List<OpenAIModel>,
    private val openAI: OpenAI = OpenAI.create(config),
    override val name: String = "OpenAI"
) : OpenAIProvider {

    private val availableModels: MutableList<OpenAIModel> = models.toMutableList()

    override fun supports(model: OpenAIModel): Boolean {
        return availableModels.any { it.value == model.value }
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
    config: OpenAIConfig,
    models: List<OpenAIModel>,
    openAI: OpenAI = OpenAI.create(config)
): OpenAIProvider {
    return DefaultOpenAIProvider(config, models, openAI)
}
