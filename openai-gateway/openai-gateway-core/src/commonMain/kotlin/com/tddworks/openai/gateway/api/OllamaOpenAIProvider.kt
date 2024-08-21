package com.tddworks.openai.gateway.api

import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.api.*
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.serialization.ExperimentalSerializationApi

@OptIn(ExperimentalSerializationApi::class)
class OllamaOpenAIProvider(
    private val client: Ollama,
    override val name: String = "Ollama"
) : OpenAIProvider {
    /**
     * Check if the given OpenAIModel is supported by the available models.
     * @param openAIModel The OpenAIModel to check for support.
     * @return true if the model is supported, false otherwise.
     */
    override fun supports(openAIModel: OpenAIModel): Boolean {
        return OllamaModel.availableModels.any { it.value == openAIModel.value }
    }

    /**
     * Override function to fetch completions from OpenAI API based on the given ChatCompletionRequest
     * @param request the ChatCompletionRequest object containing information needed to generate completions
     * @return OpenAIChatCompletion object containing completions generated from the OpenAI API
     */
    override suspend fun chatCompletions(request: ChatCompletionRequest): ChatCompletion {
        val ollamaChatRequest = request.toOllamaChatRequest()
        return client.request(ollamaChatRequest).let {
            it.toOpenAIChatCompletion()
        }
    }

    /**
     * A function that streams completions for chat based on the given ChatCompletionRequest
     *
     * @param request The ChatCompletionRequest containing the request details
     * @return A Flow of OpenAIChatCompletionChunk objects representing the completions
     */
    override fun streamChatCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        return client.stream(request.toOllamaChatRequest())
            .transform {
                emit(it.toOpenAIChatCompletionChunk())
            }
    }

    override suspend fun completions(request: CompletionRequest): Completion {
        return client.request(request.toOllamaGenerateRequest()).let {
            it.toOpenAICompletion()
        }
    }
}