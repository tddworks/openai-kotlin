package com.tddworks.anthropic.api.internal

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default


class AnthropicApi(
    private val apiKey: String,
    private val apiURL: String,
    private val anthropicVersion: String,
) : Anthropic, Messages by DefaultMessagesApi(
    AnthropicConfig(
        apiKey = { apiKey },
        anthropicVersion = { anthropicVersion }
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

    override fun anthropicVersion(): String {
        return anthropicVersion
    }

}