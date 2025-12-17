package com.tddworks.ollama.api

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.*
import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.chat.internal.DefaultOllamaChatApi
import com.tddworks.ollama.api.generate.OllamaGenerate
import com.tddworks.ollama.api.generate.internal.DefaultOllamaGenerateApi
import com.tddworks.ollama.api.internal.OllamaApi
import com.tddworks.ollama.api.json.JsonLenient

/** Interface for interacting with the Ollama API. */
interface Ollama : OllamaChat, OllamaGenerate {

    companion object {
        const val BASE_URL = "localhost"
        const val PORT = 11434
        const val PROTOCOL = "http"

        /**
         * Creates an Ollama client with the specified configuration.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = Ollama.create()  // Uses default localhost:11434
         * val client = Ollama.create(baseUrl = "192.168.1.100", port = 11434)
         * ```
         * ```swift
         * // Swift
         * let client = Ollama.create()
         * let client = Ollama.create(baseUrl: "192.168.1.100", port: 11434)
         * ```
         *
         * @param baseUrl The host of the Ollama server. Defaults to "localhost".
         * @param port The port of the Ollama server. Defaults to 11434.
         * @param protocol The protocol (http/https). Defaults to "http".
         * @return An Ollama client instance.
         */
        fun create(
            baseUrl: String = BASE_URL,
            port: Int = PORT,
            protocol: String = PROTOCOL,
        ): Ollama =
            create(OllamaConfig(baseUrl = { baseUrl }, port = { port }, protocol = { protocol }))

        /**
         * Creates an Ollama client with dynamic configuration. Use this when your host or port may
         * change at runtime.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val client = Ollama.create(
         *     baseUrl = { settings.ollamaHost },
         *     port = { settings.ollamaPort }
         * )
         * ```
         * ```swift
         * // Swift
         * let client = Ollama.create(
         *     baseUrl: { Settings.shared.ollamaHost },
         *     port: { Settings.shared.ollamaPort }
         * )
         * ```
         *
         * @param baseUrl A function that returns the host.
         * @param port A function that returns the port.
         * @param protocol A function that returns the protocol.
         * @return An Ollama client instance.
         */
        fun create(
            baseUrl: () -> String,
            port: () -> Int = { PORT },
            protocol: () -> String = { PROTOCOL },
        ): Ollama = create(OllamaConfig(baseUrl = baseUrl, port = port, protocol = protocol))

        fun create(ollamaConfig: OllamaConfig): Ollama {
            val requester =
                HttpRequester.default(
                    createHttpClient(
                        connectionConfig =
                            HostPortConnectionConfig(
                                protocol = ollamaConfig.protocol,
                                port = ollamaConfig.port,
                                host = ollamaConfig.baseUrl,
                            ),
                        // get from commonModule
                        features = ClientFeatures(json = JsonLenient),
                    )
                )
            val ollamaChat = DefaultOllamaChatApi(requester = requester)
            val ollamaGenerate = DefaultOllamaGenerateApi(requester = requester)

            return OllamaApi(
                config = ollamaConfig,
                ollamaChat = ollamaChat,
                ollamaGenerate = ollamaGenerate,
            )
        }
    }

    /**
     * This function returns the base URL as a string.
     *
     * @return a string representing the base URL
     */
    fun baseUrl(): String

    /**
     * This function returns the port as an integer.
     *
     * @return an integer representing the port
     */
    fun port(): Int

    /**
     * This function returns the protocol as a string.
     *
     * @return a string representing the protocol
     */
    fun protocol(): String
}
