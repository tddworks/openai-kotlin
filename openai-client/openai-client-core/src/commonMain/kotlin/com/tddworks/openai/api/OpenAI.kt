package com.tddworks.openai.api

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.di.createJson
import com.tddworks.di.getInstance
import com.tddworks.openai.api.chat.api.Chat
import com.tddworks.openai.api.chat.internal.default
import com.tddworks.openai.api.images.api.Images
import com.tddworks.openai.api.images.internal.default
import com.tddworks.openai.api.legacy.completions.api.Completions
import com.tddworks.openai.api.legacy.completions.api.internal.default

interface OpenAI : Chat, Images, Completions {
    companion object {
        const val BASE_URL = "https://api.openai.com"

        fun default(config: OpenAIConfig): OpenAI {
            val requester = HttpRequester.default(
                createHttpClient(
                    connectionConfig = UrlBasedConnectionConfig(config.baseUrl),
                    authConfig = AuthConfig(config.apiKey),
                    features = ClientFeatures(json = createJson())
                )
            )
            return default(requester)
        }

        fun default(
            requester: HttpRequester,
            chatCompletionPath: String = Chat.CHAT_COMPLETIONS_PATH
        ): OpenAI {
            val chatApi = Chat.default(
                requester = requester,
                chatCompletionPath = chatCompletionPath
            )

            val imagesApi = Images.default(
                requester = requester
            )

            val completionsApi = Completions.default(
                requester = requester
            )

            return object : OpenAI, Chat by chatApi, Images by imagesApi,
                Completions by completionsApi {}
        }
    }
}

class OpenAIApi : OpenAI, Chat by getInstance(), Images by getInstance(),
    Completions by getInstance()