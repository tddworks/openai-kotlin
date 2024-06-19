package com.tddworks.ollama.api

import com.tddworks.ollama.api.chat.OllamaChat
import com.tddworks.ollama.api.generate.OllamaGenerate
import com.tddworks.ollama.api.internal.OllamaApi

/**
 * Interface for interacting with the Ollama API.
 */
interface Ollama : OllamaChat, OllamaGenerate {

    companion object {
        const val BASE_URL = "localhost"
        const val PORT = 11434
        const val PROTOCOL = "http"
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

fun Ollama(
    baseUrl: () -> String = { Ollama.BASE_URL },
    port: () -> Int = { Ollama.PORT },
    protocol: () -> String = { Ollama.PROTOCOL },
): Ollama {
    return OllamaApi(
        baseUrl = baseUrl(),
        port = port(),
        protocol = protocol()
    )
}