package com.tddworks.openai.api

import com.tddworks.openai.api.OpenAI.Companion.BASE_URL
import com.tddworks.openai.api.chat.Chat
import com.tddworks.openai.api.chat.internal.DefaultChatApi
import com.tddworks.openai.api.images.Images
import com.tddworks.openai.api.images.internal.DefaultImagesApi
import com.tddworks.openai.api.internal.network.ktor.HttpRequester
import com.tddworks.openai.api.internal.network.ktor.default
import com.tddworks.openai.api.internal.network.ktor.internal.createHttpClient
import getPlatform

interface OpenAI : Chat, Images {
    companion object {
        const val BASE_URL = "api.openai.com"
    }
}

fun OpenAI(token: String): OpenAI = OpenAIApi(
    HttpRequester.default(
        createHttpClient(
            url = BASE_URL,
            token = token,
            engine = getPlatform().engine
        )
    ),
)

class OpenAIApi(private val requester: HttpRequester) : OpenAI,
    Chat by DefaultChatApi(
        requester
    ), Images by DefaultImagesApi(
        requester
    )