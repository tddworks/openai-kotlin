package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.internal.AnthropicApi
import com.tddworks.anthropic.api.messages.api.Messages

interface Anthropic : Messages {
    companion object {
        const val BASE_URL = "api.anthropic.com"
    }

    fun apiKey(): String
    fun baseUrl(): String
    fun anthropicVersion(): String
}

fun Anthropic(
    apiKey: String = "CONFIGURE_ME",
    baseUrl: String = Anthropic.BASE_URL,
    anthropicVersion: String = "2023-06-01",
): Anthropic = AnthropicApi(
    apiKey = apiKey,
    apiURL = baseUrl,
    anthropicVersion = anthropicVersion
)