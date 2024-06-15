@file:OptIn(ExperimentalSerializationApi::class)

package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.Model
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class DefaultOpenAIProvider(
    config: OpenAIConfig,
    models: List<Model>,
    private val openAI: OpenAI = OpenAI.create(config)
) : OpenAIProvider {
    private val availableModels: MutableList<Model> = models.toMutableList()

    override fun supports(model: Model): Boolean {
        return availableModels.any { it.value == model.value }
    }

    override suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion {
        return openAI.chatCompletions(request)
    }

    override fun streamChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        return openAI.streamChatCompletions(request)
    }
}
