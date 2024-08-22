package com.tddworks.openai.gateway.api.internal

import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

class AnthropicOpenAIProviderConfig(
    val anthropicVersion: () -> String = { "2023-06-01" },
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { "api.anthropic.com" }
) : OpenAIProviderConfig

fun AnthropicOpenAIProviderConfig.toAnthropicOpenAIConfig() =
    AnthropicConfig(
        apiKey = apiKey,
        baseUrl = baseUrl,
        anthropicVersion = anthropicVersion
    )

fun OpenAIProviderConfig.Companion.anthropic(
    apiKey: () -> String,
    baseUrl: () -> String = { "api.anthropic.com" },
    anthropicVersion: () -> String = { "2023-06-01" }
) = AnthropicOpenAIProviderConfig(anthropicVersion, apiKey, baseUrl)