package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.legacy.completions.api.Completions

/**
 * Interface for connecting to the OpenAI Gateway to chat.
 */
interface OpenAIGateway : Chat, Completions, Images {
    fun updateProvider(
        id: String,
        name: String,
        config: OpenAIProviderConfig,
        models: List<OpenAIModel>
    )

    fun addProvider(provider: OpenAIProvider): OpenAIGateway
    fun removeProvider(id: String)
    fun getProviders(): List<OpenAIProvider>
}