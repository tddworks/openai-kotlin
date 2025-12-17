package com.tddworks.ollama.darwin.api

import com.tddworks.ollama.api.Ollama

/**
 * Creates an Ollama client with static configuration.
 *
 * Swift usage:
 * ```swift
 * import OllamaClient
 *
 * let client = Ollama()
 * let client = Ollama(baseUrl: "192.168.1.100", port: 11434)
 * ```
 */
fun Ollama(
    baseUrl: String = Ollama.BASE_URL,
    port: Int = Ollama.PORT,
    protocol: String = Ollama.PROTOCOL,
): Ollama = Ollama.create(baseUrl = baseUrl, port = port, protocol = protocol)

/**
 * Creates an Ollama client with dynamic configuration.
 * Use this when your host or port may change at runtime.
 *
 * Swift usage:
 * ```swift
 * let client = Ollama(
 *     baseUrl: { Settings.shared.ollamaHost }
 * )
 * ```
 */
fun Ollama(
    baseUrl: () -> String,
    port: () -> Int = { Ollama.PORT },
    protocol: () -> String = { Ollama.PROTOCOL },
): Ollama = Ollama.create(baseUrl = baseUrl, port = port, protocol = protocol)
