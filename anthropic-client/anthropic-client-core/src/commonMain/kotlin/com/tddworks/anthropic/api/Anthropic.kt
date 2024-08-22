package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.internal.AnthropicApi
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.anthropic.api.messages.api.internal.JsonLenient
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.di.createJson
import com.tddworks.di.getInstance

/**
 * Interface for interacting with the Anthropic API.
 */
interface Anthropic : Messages {

    /**
     * Companion object containing a constant variable for the base URL of the API.
     */
    companion object {
        const val BASE_URL = "api.anthropic.com"
        const val ANTHROPIC_VERSION = "2023-06-01"

        /**
         * Creates an instance of Anthropic API with the provided configurations.
         * @return an instance of Anthropic API configured with the provided settings.
         */
        fun create(anthropicConfig: AnthropicConfig): Anthropic {

            val requester = HttpRequester.default(
                createHttpClient(
                    host = anthropicConfig.baseUrl,
                    // get from commonModule
                    json = JsonLenient,
                )
            )
            val messages = DefaultMessagesApi(
                anthropicConfig = anthropicConfig,
                requester = requester
            )

            return AnthropicApi(anthropicConfig = anthropicConfig, messages = messages)
        }
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