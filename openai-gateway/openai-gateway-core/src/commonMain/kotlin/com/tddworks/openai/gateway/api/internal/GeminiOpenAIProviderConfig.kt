package com.tddworks.openai.gateway.api.internal

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.gemini.api.textGeneration.api.GeminiConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

class GeminiOpenAIProviderConfig(
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { Gemini.BASE_URL }
) : OpenAIProviderConfig

fun OpenAIProviderConfig.toGeminiConfig() =
    GeminiConfig(
        apiKey = apiKey,
        baseUrl = baseUrl,
    )

fun OpenAIProviderConfig.Companion.gemini(
    apiKey: () -> String,
    baseUrl: () -> String = { Anthropic.BASE_URL },
) = GeminiOpenAIProviderConfig(apiKey, baseUrl)