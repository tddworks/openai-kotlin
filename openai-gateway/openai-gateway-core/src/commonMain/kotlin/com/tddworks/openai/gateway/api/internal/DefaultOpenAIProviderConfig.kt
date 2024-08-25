package com.tddworks.openai.gateway.api.internal

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.gateway.api.OpenAIProviderConfig

data class DefaultOpenAIProviderConfig(
    override val apiKey: () -> String,
    override val baseUrl: () -> String = { OpenAI.BASE_URL }
) : OpenAIProviderConfig

fun OpenAIProviderConfig.toOpenAIConfig() = OpenAIConfig(apiKey, baseUrl)

fun OpenAIProviderConfig.Companion.default(
    apiKey: () -> String,
    baseUrl: () -> String = { OpenAI.BASE_URL }
) = DefaultOpenAIProviderConfig(apiKey, baseUrl)