package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.legacy.completions.api.Completions

/**
 * Represents a provider for the OpenAI chat functionality.
 */
interface OpenAIProvider : Chat, Completions {
    val name: String

    val models: List<OpenAIModel>

    /**
     * Determines if the provided model is supported or not.
     *
     * @param model The model to check for support
     * @return true if the model is supported, false otherwise
     */
    fun supports(model: OpenAIModel): Boolean

    companion object
}