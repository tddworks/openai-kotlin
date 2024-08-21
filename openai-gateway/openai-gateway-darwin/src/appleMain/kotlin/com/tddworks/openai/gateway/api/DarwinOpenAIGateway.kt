package com.tddworks.openai.gateway.api

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.gateway.api.internal.anthropic
import com.tddworks.openai.gateway.api.internal.default
import com.tddworks.openai.gateway.api.internal.ollama
import com.tddworks.openai.gateway.di.initOpenAIGateway

/**
 * An object for initializing and configuring the OpenAI Gateway.
 */
object DarwinOpenAIGateway {

    /**
     * Initializes an OpenAI gateway with the specified configurations.
     *
     * @param openAIBaseUrl function that provides the base URL for OpenAI services
     * @param openAIKey function that provides the API key for OpenAI services
     * @param anthropicBaseUrl function that provides the base URL for Anthropic services
     * @param anthropicKey function that provides the API key for Anthropic services
     * @param anthropicVersion function that provides the version for Anthropic services
     * @param ollamaBaseUrl function that provides the base URL for Ollama services
     * @param ollamaPort function that provides the port for Ollama services
     * @param ollamaProtocol function that provides the protocol (e.g. http, https) for Ollama services
     *
     * @return a new instance of OpenAIGateway initialized with the provided configurations
     */
    fun openAIGateway(
        openAIBaseUrl: () -> String = { OpenAI.BASE_URL },
        openAIKey: () -> String = { "CONFIGURE_ME" },
        anthropicBaseUrl: () -> String = { "api.anthropic.com" },
        anthropicKey: () -> String = { "CONFIGURE_ME" },
        anthropicVersion: () -> String = { "2023-06-01" },
        ollamaBaseUrl: () -> String = { "localhost" },
        ollamaPort: () -> Int = { 8080 },
        ollamaProtocol: () -> String = { "http" },
    ) = initOpenAIGateway(
        openAIConfig = OpenAIProviderConfig.default(
            baseUrl = openAIBaseUrl,
            apiKey = openAIKey
        ),
        anthropicConfig = OpenAIProviderConfig.anthropic(
            baseUrl = anthropicBaseUrl,
            apiKey = anthropicKey,
            anthropicVersion = anthropicVersion
        ),
        ollamaConfig = OpenAIProviderConfig.ollama(
            baseUrl = ollamaBaseUrl,
            port = ollamaPort,
            protocol = ollamaProtocol
        )
    )
}
