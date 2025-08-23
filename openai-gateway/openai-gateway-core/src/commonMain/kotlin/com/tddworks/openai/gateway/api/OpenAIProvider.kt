package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.legacy.completions.api.Completions

/** Represents a provider for the OpenAI chat functionality. */
interface OpenAIProvider : Chat, Completions, Images {

    /** The id of the provider. */
    val id: String

    /** The name of the provider. */
    val name: String

    /** The configuration for the provider. */
    val config: OpenAIProviderConfig

    companion object
}
