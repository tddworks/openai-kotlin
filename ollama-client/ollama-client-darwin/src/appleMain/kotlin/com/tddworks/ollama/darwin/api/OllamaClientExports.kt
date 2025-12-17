package com.tddworks.ollama.darwin.api

/**
 * Creates an Ollama client with the specified configuration.
 *
 * Swift usage:
 * ```swift
 * import OllamaClient
 *
 * let client = Ollama.shared.create()
 * let client = Ollama.shared.create(baseUrl: "192.168.1.100", port: 11434)
 * ```
 */
object Ollama {
    /**
     * Creates an Ollama client with static configuration.
     */
    fun create(
        baseUrl: String = com.tddworks.ollama.api.Ollama.BASE_URL,
        port: Int = com.tddworks.ollama.api.Ollama.PORT,
        protocol: String = com.tddworks.ollama.api.Ollama.PROTOCOL,
    ): com.tddworks.ollama.api.Ollama =
        com.tddworks.ollama.api.Ollama.create(baseUrl = baseUrl, port = port, protocol = protocol)

    /**
     * Creates an Ollama client with dynamic configuration.
     * Use this when your host or port may change at runtime.
     */
    fun create(
        baseUrl: () -> String,
        port: () -> Int = { com.tddworks.ollama.api.Ollama.PORT },
        protocol: () -> String = { com.tddworks.ollama.api.Ollama.PROTOCOL },
    ): com.tddworks.ollama.api.Ollama =
        com.tddworks.ollama.api.Ollama.create(baseUrl = baseUrl, port = port, protocol = protocol)
}
