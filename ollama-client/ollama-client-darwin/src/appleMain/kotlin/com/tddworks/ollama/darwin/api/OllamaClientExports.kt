@file:Suppress("unused")

package com.tddworks.ollama.darwin.api

import com.tddworks.ollama.api.Ollama

/**
 * Creates an Ollama client with the specified configuration.
 *
 * Swift usage:
 * ```swift
 * import OllamaClient
 *
 * let client = OllamaClient.create()
 * let client = OllamaClient.create(baseUrl: "192.168.1.100", port: 11434)
 * ```
 */
object OllamaClient {
    /**
     * Creates an Ollama client with static configuration.
     */
    fun create(
        baseUrl: String = Ollama.BASE_URL,
        port: Int = Ollama.PORT,
        protocol: String = Ollama.PROTOCOL,
    ): Ollama = Ollama.create(baseUrl = baseUrl, port = port, protocol = protocol)

    /**
     * Creates an Ollama client with dynamic configuration.
     * Use this when your host or port may change at runtime.
     */
    fun create(
        baseUrl: () -> String,
        port: () -> Int = { Ollama.PORT },
        protocol: () -> String = { Ollama.PROTOCOL },
    ): Ollama = Ollama.create(baseUrl = baseUrl, port = port, protocol = protocol)
}
