package com.tddworks.openai.darwin.api

/**
 * Creates an OpenAI client with the specified API key and base URL.
 *
 * Swift usage:
 * ```swift
 * import OpenAIClient
 *
 * let client = OpenAI.shared.create(apiKey: "your-api-key")
 * let client = OpenAI.shared.create(apiKey: "your-api-key", baseUrl: "https://custom.api.com")
 * ```
 */
object OpenAI {
    /**
     * Creates an OpenAI client with static configuration.
     */
    fun create(
        apiKey: String,
        baseUrl: String = com.tddworks.openai.api.OpenAI.BASE_URL,
    ): com.tddworks.openai.api.OpenAI = com.tddworks.openai.api.OpenAI.create(apiKey = apiKey, baseUrl = baseUrl)

    /**
     * Creates an OpenAI client with dynamic configuration.
     * Use this when your API key or base URL may change at runtime.
     */
    fun create(
        apiKey: () -> String,
        baseUrl: () -> String = { com.tddworks.openai.api.OpenAI.BASE_URL },
    ): com.tddworks.openai.api.OpenAI = com.tddworks.openai.api.OpenAI.create(apiKey = apiKey, baseUrl = baseUrl)
}
