@file:Suppress("unused")

package com.tddworks.openai.gateway.darwin.api

import com.tddworks.openai.gateway.api.OpenAIGateway

/**
 * Re-exports for Swift consumers.
 * All factory methods are available via OpenAIGateway.Companion.
 *
 * Usage:
 * ```swift
 * import OpenAIGateway
 *
 * let gateway = OpenAIGateway.create(
 *     openAIKey: "your-openai-key",
 *     anthropicKey: "your-anthropic-key"
 * )
 * ```
 */
typealias OpenAIGatewayCompanion = OpenAIGateway.Companion
