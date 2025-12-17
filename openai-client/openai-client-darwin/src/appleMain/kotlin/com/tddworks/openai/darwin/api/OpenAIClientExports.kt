@file:Suppress("unused")

package com.tddworks.openai.darwin.api

import com.tddworks.openai.api.OpenAI

/**
 * Creates an OpenAI client with the specified API key and base URL.
 *
 * Swift usage:
 * ```swift
 * import OpenAIClient
 *
 * let client = OpenAIClient.create(apiKey: "your-api-key")
 * let client = OpenAIClient.create(apiKey: "your-api-key", baseUrl: "https://custom.api.com")
 * ```
 */
object OpenAIClient {
    /**
     * Creates an OpenAI client with static configuration.
     */
    fun create(
        apiKey: String,
        baseUrl: String = OpenAI.BASE_URL,
    ): OpenAI = OpenAI.create(apiKey = apiKey, baseUrl = baseUrl)

    /**
     * Creates an OpenAI client with dynamic configuration.
     * Use this when your API key or base URL may change at runtime.
     */
    fun create(
        apiKey: () -> String,
        baseUrl: () -> String = { OpenAI.BASE_URL },
    ): OpenAI = OpenAI.create(apiKey = apiKey, baseUrl = baseUrl)
}
