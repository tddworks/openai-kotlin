package com.tddworks.openai.darwin.api

import com.tddworks.openai.api.OpenAI

/**
 * Creates an OpenAI client with static configuration.
 *
 * Swift usage:
 * ```swift
 * import OpenAIClient
 *
 * let client = OpenAIClient(apiKey: "your-api-key")
 * let client = OpenAIClient(apiKey: "your-api-key", baseUrl: "https://custom.api.com")
 * ```
 */
fun OpenAIClient(
    apiKey: String,
    baseUrl: String = OpenAI.BASE_URL,
): OpenAI = OpenAI.create(apiKey = apiKey, baseUrl = baseUrl)

/**
 * Creates an OpenAI client with dynamic configuration.
 * Use this when your API key or base URL may change at runtime.
 *
 * Swift usage:
 * ```swift
 * let client = OpenAIClient(
 *     apiKey: { Settings.shared.apiKey },
 *     baseUrl: { Settings.shared.baseUrl }
 * )
 * ```
 */
fun OpenAIClient(
    apiKey: () -> String,
    baseUrl: () -> String = { OpenAI.BASE_URL },
): OpenAI = OpenAI.create(apiKey = apiKey, baseUrl = baseUrl)
