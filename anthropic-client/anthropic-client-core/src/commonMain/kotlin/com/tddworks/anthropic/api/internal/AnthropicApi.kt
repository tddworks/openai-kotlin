package com.tddworks.anthropic.api.internal

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.di.getInstance


class AnthropicApi(
    private val apiKey: String,
    private val apiURL: String,
    private val anthropicVersion: String,
) : Anthropic, Messages by getInstance() {
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