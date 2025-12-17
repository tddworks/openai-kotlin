package com.tddworks.anthropic.darwin.api

import com.tddworks.anthropic.api.Anthropic

/**
 * Creates an Anthropic client with static configuration.
 *
 * Swift usage:
 * ```swift
 * import AnthropicClient
 *
 * let client = Anthropic(apiKey: "your-api-key")
 * ```
 */
fun Anthropic(
    apiKey: String,
    baseUrl: String = Anthropic.BASE_URL,
    anthropicVersion: String = Anthropic.ANTHROPIC_VERSION,
): Anthropic = Anthropic.create(
    apiKey = apiKey,
    baseUrl = baseUrl,
    anthropicVersion = anthropicVersion
)

/**
 * Creates an Anthropic client with dynamic configuration.
 * Use this when your API key or settings may change at runtime.
 *
 * Swift usage:
 * ```swift
 * let client = Anthropic(
 *     apiKey: { Settings.shared.apiKey }
 * )
 * ```
 */
fun Anthropic(
    apiKey: () -> String,
    baseUrl: () -> String = { Anthropic.BASE_URL },
    anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
): Anthropic = Anthropic.create(
    apiKey = apiKey,
    baseUrl = baseUrl,
    anthropicVersion = anthropicVersion
)
