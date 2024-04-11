package com.tddworks.openai.gateway.api.internal

import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.gateway.api.OpenAIGateway
import com.tddworks.openai.gateway.api.OpenAIProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi


/**
 * DefaultOpenAIGateway is a class that implements the OpenAIGateway interface and acts as a mediator between multiple OpenAI providers.
 * It delegates completions and streaming completions requests to the appropriate provider based on the model specified in the request.
 */
@ExperimentalSerializationApi
class DefaultOpenAIGateway(
    private val providers: List<OpenAIProvider>,
) : OpenAIGateway {
    /**
     * This function is called to get completions for a chat based on the given request.
     *
     * @param request The request containing the model for which completions are needed.
     * @return A ChatCompletion object containing the completions for the provided request.
     */
    override suspend fun completions(request: ChatCompletionRequest): ChatCompletion {
        providers.first {
            it.supports(request.model)
        }.let {
            return it.completions(request)
        }
    }

    /**
     * This function overrides the streamCompletions method defined in the parent class.
     * It takes a ChatCompletionRequest object as a parameter and returns a Flow of ChatCompletionChunk objects.
     * The function will find the first provider that supports the model in the request and then call the streamCompletions function on that provider.
     * @param request a ChatCompletionRequest object containing the model for which completions are requested
     * @return a Flow of ChatCompletionChunk objects representing the completions for the input model
     */
    override fun streamCompletions(request: ChatCompletionRequest): Flow<ChatCompletionChunk> {
        providers.first {
            it.supports(request.model)
        }.let {
            return it.streamCompletions(request)
        }
    }
}