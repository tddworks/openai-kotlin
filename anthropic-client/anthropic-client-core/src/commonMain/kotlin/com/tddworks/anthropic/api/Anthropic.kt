package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.Anthropic.Companion.BASE_URL
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default

interface Anthropic : Messages {
    companion object {
        const val BASE_URL = "api.anthropic.com"
    }

    fun apiKey(): String
    fun baseUrl(): String
}

fun Anthropic(
    apiKey: String = "CONFIGURE_ME",
    baseUrl: String = BASE_URL,
): Anthropic = AnthropicApi(
    apiKey = apiKey,
    apiURL = baseUrl
)

class AnthropicApi(
    private val apiKey: String,
    private val apiURL: String,
) : Anthropic, Messages by DefaultMessagesApi(
    AnthropicConfig(
        apiKey = { apiKey },
        anthropicVersion = { "2023-06-01" }
    ),
    HttpRequester.default(
        createHttpClient(
            url = apiURL
        )
    )
) {
    override fun apiKey(): String {
        return apiKey
    }

    override fun baseUrl(): String {
        return apiURL
    }

}