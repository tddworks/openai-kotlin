@file:Suppress("unused")

package com.tddworks.gemini.darwin.api

import com.tddworks.gemini.api.textGeneration.api.Gemini

/**
 * Creates a Gemini client with the specified configuration.
 *
 * Swift usage:
 * ```swift
 * import GeminiClient
 *
 * let client = Gemini.shared.create(apiKey: "your-api-key")
 * ```
 */
object Gemini {
    /**
     * Creates a Gemini client with static configuration.
     */
    fun create(
        apiKey: String,
        baseUrl: String = com.tddworks.gemini.api.textGeneration.api.Gemini.BASE_URL,
    ): com.tddworks.gemini.api.textGeneration.api.Gemini =
        com.tddworks.gemini.api.textGeneration.api.Gemini.create(apiKey = apiKey, baseUrl = baseUrl)

    /**
     * Creates a Gemini client with dynamic configuration.
     * Use this when your API key or base URL may change at runtime.
     */
    fun create(
        apiKey: () -> String,
        baseUrl: () -> String = { com.tddworks.gemini.api.textGeneration.api.Gemini.BASE_URL },
    ): com.tddworks.gemini.api.textGeneration.api.Gemini =
        com.tddworks.gemini.api.textGeneration.api.Gemini.create(apiKey = apiKey, baseUrl = baseUrl)
}
