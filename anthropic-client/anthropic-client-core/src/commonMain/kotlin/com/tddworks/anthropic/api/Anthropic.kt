package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.internal.AnthropicApi
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.anthropic.api.messages.api.internal.JsonLenient
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*

/** Interface for interacting with the Anthropic API. */
interface Anthropic : Messages {

    /** Companion object containing a constant variable for the base URL of the API. */
    companion object {
        const val BASE_URL = "https://api.anthropic.com"
        const val ANTHROPIC_VERSION = "2023-06-01"

        /**
         * Creates an Anthropic client with the specified configuration.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = Anthropic.create(apiKey = "your-api-key")
         * val client = Anthropic.create(
         *     apiKey = "your-api-key",
         *     baseUrl = "https://custom.api.com",
         *     anthropicVersion = "2023-06-01"
         * )
         * ```
         * ```swift
         * // Swift
         * let client = Anthropic.create(apiKey: "your-api-key")
         * ```
         *
         * @param apiKey The API key for authentication.
         * @param baseUrl The base URL of the Anthropic API. Defaults to BASE_URL.
         * @param anthropicVersion The Anthropic API version. Defaults to ANTHROPIC_VERSION.
         * @return An Anthropic client instance.
         */
        fun create(
            apiKey: String,
            baseUrl: String = BASE_URL,
            anthropicVersion: String = ANTHROPIC_VERSION,
        ): Anthropic =
            create(
                AnthropicConfig(
                    apiKey = { apiKey },
                    baseUrl = { baseUrl },
                    anthropicVersion = { anthropicVersion },
                )
            )

        /**
         * Creates an Anthropic client with dynamic configuration. Use this when your API key or
         * settings may change at runtime.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = Anthropic.create(
         *     apiKey = { settings.apiKey },
         *     baseUrl = { settings.baseUrl }
         * )
         * ```
         * ```swift
         * // Swift
         * let client = Anthropic.create(
         *     apiKey: { Settings.shared.apiKey },
         *     baseUrl: { Settings.shared.baseUrl }
         * )
         * ```
         *
         * @param apiKey A function that returns the API key.
         * @param baseUrl A function that returns the base URL.
         * @param anthropicVersion A function that returns the API version.
         * @return An Anthropic client instance.
         */
        fun create(
            apiKey: () -> String,
            baseUrl: () -> String = { BASE_URL },
            anthropicVersion: () -> String = { ANTHROPIC_VERSION },
        ): Anthropic =
            create(
                AnthropicConfig(
                    apiKey = apiKey,
                    baseUrl = baseUrl,
                    anthropicVersion = anthropicVersion,
                )
            )

        /**
         * Creates an instance of Anthropic API with the provided configurations.
         *
         * @return an instance of Anthropic API configured with the provided settings.
         */
        fun create(anthropicConfig: AnthropicConfig): Anthropic {

            val requester =
                HttpRequester.default(
                    createHttpClient(
                        connectionConfig = UrlBasedConnectionConfig(anthropicConfig.baseUrl),
                        authConfig = AuthConfig(anthropicConfig.apiKey),
                        // get from commonModule
                        features = ClientFeatures(json = JsonLenient),
                    )
                )
            val messages =
                DefaultMessagesApi(anthropicConfig = anthropicConfig, requester = requester)

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
     * Returns the anthropic version of the provided class. The anthropic version is a String
     * representation of the class name with "Anthropic" prefixed to it.
     *
     * @return The anthropic version of the class as a String.
     */
    fun anthropicVersion(): String
}
