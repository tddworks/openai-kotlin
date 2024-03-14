package com.tddworks.openai.api

import com.tddworks.openai.api.OpenAI.Companion.BASE_URL
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.internal.DefaultChatApi
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.images.internal.DefaultImagesApi
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.common.network.api.ktor.internal.createHttpClient
import io.ktor.client.engine.*

interface OpenAI : Chat, Images {
    companion object {
        const val BASE_URL = "api.openai.com"
    }
}

fun OpenAI(token: String, engine: HttpClientEngineFactory<HttpClientEngineConfig>): OpenAI = OpenAIApi(
    HttpRequester.default(
        createHttpClient(
            url = BASE_URL,
            token = token,
            engine = engine
        )
    ),
)

class OpenAIApi(private val requester: HttpRequester) : OpenAI,
    Chat by DefaultChatApi(
        requester
    ), Images by DefaultImagesApi(
        requester
    )