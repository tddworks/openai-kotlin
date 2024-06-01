package com.tddworks.openai.gateway.api

import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.api.toOllamaChatRequest
import com.tddworks.ollama.api.chat.api.toOpenAIChatCompletion
import com.tddworks.ollama.api.chat.api.toOpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.Model
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class OllamaOpenAIProvider(private val client: Ollama) : OpenAIProvider {
    /**
     * Check if the given OpenAIModel is supported by the available models.
     * @param model The OpenAIModel to check for support.
     * @return true if the model is supported, false otherwise.
     */
    override fun supports(model: Model): Boolean {
        return OllamaModel.availableModels.any { it.value == model.value }
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
}