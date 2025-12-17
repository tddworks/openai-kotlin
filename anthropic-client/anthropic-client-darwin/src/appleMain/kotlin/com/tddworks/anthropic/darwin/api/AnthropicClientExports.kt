@file:Suppress("unused")

package com.tddworks.anthropic.darwin.api

import com.tddworks.anthropic.api.Anthropic

/**
 * Creates an Anthropic client with the specified configuration.
 *
 * Swift usage:
 * ```swift
 * import AnthropicClient
 *
 * let client = Anthropic.shared.create(apiKey: "your-api-key")
 * ```
 */
object Anthropic {
    /**
     * Creates an Anthropic client with static configuration.
     */
    fun create(
        apiKey: String,
        baseUrl: String = com.tddworks.anthropic.api.Anthropic.BASE_URL,
        anthropicVersion: String = com.tddworks.anthropic.api.Anthropic.ANTHROPIC_VERSION,
    ): Anthropic = com.tddworks.anthropic.api.Anthropic.create(
        apiKey = apiKey,
        baseUrl = baseUrl,
        anthropicVersion = anthropicVersion
    )

    /**
     * Creates an Anthropic client with dynamic configuration.
     * Use this when your API key or settings may change at runtime.
     */
    fun create(
        apiKey: () -> String,
        baseUrl: () -> String = { com.tddworks.anthropic.api.Anthropic.BASE_URL },
        anthropicVersion: () -> String = { com.tddworks.anthropic.api.Anthropic.ANTHROPIC_VERSION },
    ): com.tddworks.anthropic.api.Anthropic = com.tddworks.anthropic.api.Anthropic.create(
        apiKey = apiKey,
        baseUrl = baseUrl,
        anthropicVersion = anthropicVersion
    )
}
