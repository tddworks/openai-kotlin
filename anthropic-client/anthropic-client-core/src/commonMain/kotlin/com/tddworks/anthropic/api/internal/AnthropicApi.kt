package com.tddworks.anthropic.api.internal

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.di.getInstance


/**
 * The Anthropic API class encapsulates the necessary properties and methods to interact with the Anthropic API.
 *
 * @property apiKey the unique identifier for your Anthropic API account
 * @property apiURL the base URL for making API requests to the Anthropic API
 * @property anthropicVersion the version of the Anthropics library being used
 */
class AnthropicApi(
    private val apiKey: String,
    private val apiURL: String,
    private val anthropicVersion: String,
    private val messages: Messages = getInstance()
) : Anthropic, Messages by messages {
    /**
     * Gets the API key.
     *
     * @return The API key string.
     */
    override fun apiKey(): String {
        return apiKey
    }

    /**
     * Returns the base URL for API requests.
     *
     * @return The base URL of the API
     */
    override fun baseUrl(): String {
        return apiURL
    }

    /**
     * Returns the anthropic version string.
     *
     * @return The anthropic version string.
     */
    override fun anthropicVersion(): String {
        return anthropicVersion
    }

}

fun Anthropic.Companion.create(
    apiKey: String,
    apiURL: String,
    anthropicVersion: String,
    messages: Messages = getInstance()
): Anthropic {
    return AnthropicApi(
        apiKey = apiKey,
        apiURL = apiURL,
        anthropicVersion = anthropicVersion,
        messages = messages
    )
}