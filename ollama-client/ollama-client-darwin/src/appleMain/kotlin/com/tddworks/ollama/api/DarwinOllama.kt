package com.tddworks.ollama.api

import com.tddworks.ollama.di.iniOllama


/**
 * Creates an instance of Ollama with the specified configuration options.
 *
 * @param port a function that returns the port number for the Ollama instance, defaults to Ollama.PORT
 * @param protocol a function that returns the protocol for the Ollama instance, defaults to Ollama.PROTOCOL
 * @param baseUrl a function that returns the base URL for the Ollama instance, defaults to Ollama.BASE_URL
 * @return an Ollama instance initialized with the provided configuration
 */
object DarwinOllama {

    /**
     * Function to create an Ollama instance with the provided configuration.
     *
     * @param port function returning an integer representing the port, defaults to Ollama.PORT
     * @param protocol function returning a string representing the protocol, defaults to Ollama.PROTOCOL
     * @param baseUrl function returning a string representing the base URL, defaults to Ollama.BASE_URL
     * @return an Ollama instance created with the provided configuration
     */
    fun ollama(
        port: () -> Int = { Ollama.PORT },
        protocol: () -> String = { Ollama.PROTOCOL },
        baseUrl: () -> String = { Ollama.BASE_URL },
    ): Ollama = iniOllama(OllamaConfig(baseUrl = baseUrl, port = port, protocol = protocol))
}