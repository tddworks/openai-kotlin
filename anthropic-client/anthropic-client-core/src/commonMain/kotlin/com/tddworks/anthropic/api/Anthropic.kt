package com.tddworks.anthropic.api

import com.tddworks.anthropic.api.Anthropic.Companion.BASE_URL
import com.tddworks.anthropic.api.messages.api.AnthropicConfig
import com.tddworks.anthropic.api.messages.api.Messages
import com.tddworks.anthropic.api.messages.api.internal.DefaultMessagesApi
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import com.tddworks.common.network.api.ktor.internal.default
import io.ktor.client.engine.*

interface Anthropic : Messages {
    companion object {
        const val BASE_URL = "klaude.asusual.life"
    }
}

fun Anthropic(apiKey: String, engine: HttpClientEngineFactory<HttpClientEngineConfig>): Anthropic = AnthropicApi(
    apiKey = apiKey,
    HttpRequester.default(
        createHttpClient(
            url = BASE_URL, engine = engine
        )
    ),
)

class AnthropicApi(
    private val apiKey: String,
    private val requester: HttpRequester,
) : Anthropic, Messages by DefaultMessagesApi(
    AnthropicConfig(
        apiKey = apiKey
    ),
    requester
)