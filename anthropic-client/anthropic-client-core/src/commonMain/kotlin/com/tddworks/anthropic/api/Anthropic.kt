package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.internal.AnthropicApi
import com.tddworks.anthropic.api.messages.api.Messages

/**
 * Interface for interacting with the Anthropic API.
 */
interface Anthropic : Messages {
    /**
     * Companion object containing a constant variable for the base URL of the API.
     */
    companion object {
        const val BASE_URL = "api.anthropic.com"
    }

    /**
     * Function to retrieve the API key.
     *
     * @return a String representing the API key
     */
    fun apiKey(): String

    /**
     * This function returns the base URL as a string.
     *
     * @return a string representing the base URL
     */
    fun baseUrl(): String

    /**
     * Returns the anthropic version of the provided class.
     * The anthropic version is a String representation of the class name with "Anthropic"
     * prefixed to it.
     *
     * @return The anthropic version of the class as a String.
     */
    fun anthropicVersion(): String
}

/**
 * Creates an instance of Anthropic API with the provided configurations.
 *
 * @param apiKey a function that returns the API key to be used for authentication. Defaults to "CONFIGURE_ME" if not provided.
 * @param baseUrl a function that returns the base URL of the API. Defaults to the value specified in the Anthropic companion object if not provided.
 * @param anthropicVersion a function that returns the version of the Anthropic API to be used. Defaults to "2023-06-01" if not provided.
 * @return an instance of Anthropic API configured with the provided settings.
 */
fun Anthropic(
    apiKey: () -> String = { "CONFIGURE_ME" },
    baseUrl: () -> String = { Anthropic.BASE_URL },
    anthropicVersion: () -> String = { "2023-06-01" },
): Anthropic {
    return AnthropicApi(
        apiKey = apiKey(),
        apiURL = baseUrl(),
        anthropicVersion = anthropicVersion()
    )
}