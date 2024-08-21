package com.tddworks.openai.gateway.api.internal

import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

data class DefaultOpenAIProviderConfig(
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { DEFAULT_BASE_URL }
) : OpenAIProviderConfig {
    companion object {
        const val DEFAULT_BASE_URL = "api.openai.com"
    }
}

fun OpenAIProviderConfig.toOpenAIConfig() = OpenAIConfig(apiKey, baseUrl)

fun OpenAIProviderConfig.Companion.default(
    apiKey: () -> String,
    baseUrl: () -> String = { DefaultOpenAIProviderConfig.DEFAULT_BASE_URL }
) = DefaultOpenAIProviderConfig(apiKey, baseUrl)