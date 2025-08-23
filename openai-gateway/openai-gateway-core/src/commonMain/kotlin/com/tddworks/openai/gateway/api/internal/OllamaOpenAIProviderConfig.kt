package com.tddworks.openai.gateway.api.internal

import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

data class OllamaOpenAIProviderConfig(
    val port: () -> Int = { 11434 },
    val protocol: () -> String = { "http" },
    override val baseUrl: () -> String = { "http//:localhost:11434" },
    override val apiKey: () -> String = { "ollama-ignore-this" },
) : OpenAIProviderConfig

fun OllamaOpenAIProviderConfig.toOllamaConfig() =
    OllamaConfig(baseUrl = baseUrl, protocol = protocol, port = port)

fun OpenAIProviderConfig.Companion.ollama(
    apiKey: () -> String = { "ollama-ignore-this" },
    baseUrl: () -> String = { "localhost" },
    protocol: () -> String = { "http" },
    port: () -> Int = { 11434 },
) = OllamaOpenAIProviderConfig(port, protocol, baseUrl, apiKey)
