@file:OptIn(ExperimentalSerializationApi::class)

package com.tddworks.openai.gateway.api.internal

import com.tddworks.azure.api.AzureAIProviderConfig
import com.tddworks.azure.api.azure
import com.tddworks.common.network.api.ktor.api.ListResponse
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.images.api.Image
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class DefaultOpenAIProvider(
    override val id: String = "openai",
    override val name: String = "OpenAI",
    override val config: OpenAIProviderConfig,
    private val openAI: OpenAI = OpenAI.default(config.toOpenAIConfig()),
) : OpenAIProvider {

    override suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion {
        return openAI.chatCompletions(request)
    }

    override fun streamChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        return openAI.streamChatCompletions(request)
    }

    override suspend fun completions(request: CompletionRequest): Completion {
        return openAI.completions(request)
    }

    override suspend fun generate(request: ImageCreate): ListResponse<Image> {
        return openAI.generate(request)
    }
}

fun OpenAIProvider.Companion.openAI(
    id: String = "openai",
    config: OpenAIProviderConfig,
    openAI: OpenAI = OpenAI.default(config.toOpenAIConfig())
): OpenAIProvider {
    return DefaultOpenAIProvider(
        id = id,
        config = config, openAI = openAI
    )
}

fun OpenAIProvider.Companion.azure(
    id: String = "azure",
    config: OpenAIProviderConfig,
    openAI: OpenAI = OpenAI.azure(config as AzureAIProviderConfig)
): OpenAIProvider {
    return DefaultOpenAIProvider(
        id = id,
        config = config, openAI = openAI
    )
}
