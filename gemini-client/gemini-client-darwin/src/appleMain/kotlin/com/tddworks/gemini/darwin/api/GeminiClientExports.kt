package com.tddworks.gemini.darwin.api

import com.tddworks.gemini.api.textGeneration.api.Gemini

/**
 * Creates a Gemini client with static configuration.
 *
 * Swift usage:
 * ```swift
 * import GeminiClient
 *
 * let client = GeminiClient(apiKey: "your-api-key")
 * ```
 */
fun GeminiClient(apiKey: String, baseUrl: String = Gemini.BASE_URL): Gemini =
    Gemini.create(apiKey = apiKey, baseUrl = baseUrl)

/**
 * Creates a Gemini client with dynamic configuration. Use this when your API key or base URL may
 * change at runtime.
 *
 * Swift usage:
 * ```swift
 * let client = GeminiClient(
 *     apiKey: { Settings.shared.apiKey }
 * )
 * ```
 */
fun GeminiClient(apiKey: () -> String, baseUrl: () -> String = { Gemini.BASE_URL }): Gemini =
    Gemini.create(apiKey = apiKey, baseUrl = baseUrl)
