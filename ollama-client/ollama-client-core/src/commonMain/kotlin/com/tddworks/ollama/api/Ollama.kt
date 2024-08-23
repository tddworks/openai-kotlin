package com.tddworks.ollama.api

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.chat.internal.DefaultOllamaChatApi
import com.tddworks.ollama.api.generate.OllamaGenerate
import com.tddworks.ollama.api.generate.internal.DefaultOllamaGenerateApi
import com.tddworks.ollama.api.internal.OllamaApi
import com.tddworks.ollama.api.json.JsonLenient

/**
 * Interface for interacting with the Ollama API.
 */
interface Ollama : OllamaChat, OllamaGenerate {

    companion object {
        const val BASE_URL = "http://localhost:11434"

        fun create(ollamaConfig: OllamaConfig): Ollama {
            val requester = HttpRequester.default(
                createHttpClient(
                    connectionConfig = UrlBasedConnectionConfig(
                        baseUrl = ollamaConfig.baseUrl,
                    ),
                    // get from commonModule
                    features = ClientFeatures(json = JsonLenient)
                )
            )
            val ollamaChat = DefaultOllamaChatApi(requester = requester)
            val ollamaGenerate = DefaultOllamaGenerateApi(requester = requester)

            return OllamaApi(
                config = ollamaConfig,
                ollamaChat = ollamaChat,
                ollamaGenerate = ollamaGenerate
            )
        }
    }

    /**
     * This function returns the base URL as a string.
     *
     * @return a string representing the base URL
     */
    fun baseUrl(): String
}