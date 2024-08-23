package com.tddworks.openai.gateway.api.internal

import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

data class OllamaOpenAIProviderConfig(
    override val baseUrl: () -> String = { "http//:localhost:11434" },
    override val apiKey: () -> String = { "ollama-ignore-this" }
) : OpenAIProviderConfig

fun OllamaOpenAIProviderConfig.toOllamaConfig() =
    OllamaConfig(baseUrl = baseUrl)

fun OpenAIProviderConfig.Companion.ollama(
    apiKey: () -> String = { "ollama-ignore-this" },
    baseUrl: () -> String = { "http//:localhost:11434" },
) = OllamaOpenAIProviderConfig(baseUrl, apiKey)