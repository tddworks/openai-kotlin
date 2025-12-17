package com.tddworks.openai.api

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.di.createJson
import com.tddworks.di.getInstance
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.internal.default
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.images.internal.default
import com.tddworks.openai.api.legacy.completions.api.Completions
import com.tddworks.openai.api.legacy.completions.api.internal.default

interface OpenAI : Chat, Images, Completions {
    companion object {
        const val BASE_URL = "https://api.openai.com"

        /**
         * Creates an OpenAI client with the specified API key and base URL.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = OpenAI.create(apiKey = "your-api-key")
         * val client = OpenAI.create(apiKey = "your-api-key", baseUrl = "https://custom.api.com")
         * ```
         * ```swift
         * // Swift
         * let client = OpenAI.create(apiKey: "your-api-key")
         * let client = OpenAI.create(apiKey: "your-api-key", baseUrl: "https://custom.api.com")
         * ```
         *
         * @param apiKey The API key for authentication.
         * @param baseUrl The base URL of the OpenAI API. Defaults to BASE_URL.
         * @return An OpenAI client instance.
         */
        fun create(apiKey: String, baseUrl: String = BASE_URL): OpenAI =
            default(OpenAIConfig(apiKey = { apiKey }, baseUrl = { baseUrl }))

        /**
         * Creates an OpenAI client with dynamic configuration. Use this when your API key or base
         * URL may change at runtime.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = OpenAI.create(
         *     apiKey = { settings.apiKey },
         *     baseUrl = { settings.baseUrl }
         * )
         * ```
         * ```swift
         * // Swift
         * let client = OpenAI.create(
         *     apiKey: { Settings.shared.apiKey },
         *     baseUrl: { Settings.shared.baseUrl }
         * )
         * ```
         *
         * @param apiKey A function that returns the API key.
         * @param baseUrl A function that returns the base URL.
         * @return An OpenAI client instance.
         */
        fun create(apiKey: () -> String, baseUrl: () -> String = { BASE_URL }): OpenAI =
            default(OpenAIConfig(apiKey = apiKey, baseUrl = baseUrl))

        fun default(config: OpenAIConfig): OpenAI {
            val requester =
                HttpRequester.default(
                    createHttpClient(
                        connectionConfig = UrlBasedConnectionConfig(config.baseUrl),
                        authConfig = AuthConfig(config.apiKey),
                        features = ClientFeatures(json = createJson()),
                    )
                )
            return default(requester)
        }

        fun default(
            requester: HttpRequester,
            chatCompletionPath: String = Chat.CHAT_COMPLETIONS_PATH,
        ): OpenAI {
            val chatApi =
                Chat.default(requester = requester, chatCompletionPath = chatCompletionPath)

            val imagesApi = Images.default(requester = requester)

            val completionsApi = Completions.default(requester = requester)

            return object :
                OpenAI, Chat by chatApi, Images by imagesApi, Completions by completionsApi {}
        }
    }
}

class OpenAIApi :
    OpenAI, Chat by getInstance(), Images by getInstance(), Completions by getInstance()
