package com.tddworks.gemini.api.textGeneration.api

import com.tddworks.di.getInstance
import com.tddworks.gemini.di.initGemini

interface Gemini : TextGeneration {
    companion object {
        const val HOST = "generativelanguage.googleapis.com"
        const val BASE_URL = "https://$HOST"

        /**
         * Creates a Gemini client with the specified configuration.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = Gemini.create(apiKey = "your-api-key")
         * val client = Gemini.create(apiKey = "your-api-key", baseUrl = "https://custom.api.com")
         * ```
         * ```swift
         * // Swift
         * let client = Gemini.create(apiKey: "your-api-key")
         * ```
         *
         * @param apiKey The API key for authentication.
         * @param baseUrl The base URL of the Gemini API. Defaults to BASE_URL.
         * @return A Gemini client instance.
         */
        fun create(apiKey: String, baseUrl: String = BASE_URL): Gemini =
            create(GeminiConfig(apiKey = { apiKey }, baseUrl = { baseUrl }))

        /**
         * Creates a Gemini client with dynamic configuration. Use this when your API key or base
         * URL may change at runtime.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = Gemini.create(
         *     apiKey = { settings.apiKey },
         *     baseUrl = { settings.baseUrl }
         * )
         * ```
         * ```swift
         * // Swift
         * let client = Gemini.create(
         *     apiKey: { Settings.shared.apiKey },
         *     baseUrl: { Settings.shared.baseUrl }
         * )
         * ```
         *
         * @param apiKey A function that returns the API key.
         * @param baseUrl A function that returns the base URL.
         * @return A Gemini client instance.
         */
        fun create(apiKey: () -> String, baseUrl: () -> String = { BASE_URL }): Gemini =
            create(GeminiConfig(apiKey = apiKey, baseUrl = baseUrl))

        fun default(): Gemini {
            return object : Gemini, TextGeneration by getInstance() {}
        }

        fun create(config: GeminiConfig): Gemini {
            return initGemini(config).koin.get<Gemini>()
        }
    }
}
