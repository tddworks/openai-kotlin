package com.tddworks.openai.gateway.api.internal

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

class AnthropicOpenAIProviderConfig(
    val anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { Anthropic.BASE_URL }
) : OpenAIProviderConfig

fun AnthropicOpenAIProviderConfig.toAnthropicOpenAIConfig() =
    AnthropicConfig(
        apiKey = apiKey,
        baseUrl = baseUrl,
        anthropicVersion = anthropicVersion
    )

fun OpenAIProviderConfig.Companion.anthropic(
    apiKey: () -> String,
    baseUrl: () -> String = { Anthropic.BASE_URL },
    anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION }
) = AnthropicOpenAIProviderConfig(anthropicVersion, apiKey, baseUrl)