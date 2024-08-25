package com.tddworks.openai.gateway.api.internal

import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

class AnthropicOpenAIProviderConfig(
    val anthropicVersion: () -> String = { "2023-06-01" },
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { DEFAULT_BASE_URL }
) : OpenAIProviderConfig {
    companion object {
        const val DEFAULT_BASE_URL = "https://api.anthropic.com"
        const val DEFAULT_ANTHROPIC_VERSION = "2023-06-01"
    }
}

fun AnthropicOpenAIProviderConfig.toAnthropicOpenAIConfig() =
    AnthropicConfig(
        apiKey = apiKey,
        baseUrl = baseUrl,
        anthropicVersion = anthropicVersion
    )

fun OpenAIProviderConfig.Companion.anthropic(
    apiKey: () -> String,
    baseUrl: () -> String = { AnthropicOpenAIProviderConfig.DEFAULT_BASE_URL },
    anthropicVersion: () -> String = { AnthropicOpenAIProviderConfig.DEFAULT_ANTHROPIC_VERSION }
) = AnthropicOpenAIProviderConfig(anthropicVersion, apiKey, baseUrl)