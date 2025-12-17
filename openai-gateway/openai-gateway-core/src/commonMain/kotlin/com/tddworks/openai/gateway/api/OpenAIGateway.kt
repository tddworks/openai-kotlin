package com.tddworks.openai.gateway.api

import com.tddworks.anthropic.api.Anthropic
import com.tddworks.common.network.api.ktor.api.ListResponse
import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.ollama.api.Ollama
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.images.api.Image
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.internal.anthropic
import com.tddworks.openai.gateway.api.internal.default
import com.tddworks.openai.gateway.api.internal.gemini
import com.tddworks.openai.gateway.api.internal.ollama
import com.tddworks.openai.gateway.di.initOpenAIGateway
import kotlinx.coroutines.flow.Flow

/** Interface for connecting to the OpenAI Gateway to chat. */
interface OpenAIGateway {

    companion object {
        /**
         * Creates an OpenAI Gateway with the specified configurations.
         *
         * The gateway provides a unified interface to multiple AI providers (OpenAI, Anthropic,
         * Gemini, Ollama).
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val gateway = OpenAIGateway.create(
         *     openAIKey = "your-openai-key",
         *     anthropicKey = "your-anthropic-key",
         *     geminiKey = "your-gemini-key"
         * )
         * ```
         * ```swift
         * // Swift
         * let gateway = OpenAIGateway.create(
         *     openAIKey: "your-openai-key",
         *     anthropicKey: "your-anthropic-key",
         *     geminiKey: "your-gemini-key"
         * )
         * ```
         *
         * @param openAIKey The API key for OpenAI services.
         * @param openAIBaseUrl The base URL for OpenAI services. Defaults to OpenAI.BASE_URL.
         * @param anthropicKey The API key for Anthropic services.
         * @param anthropicBaseUrl The base URL for Anthropic services. Defaults to
         *   Anthropic.BASE_URL.
         * @param anthropicVersion The version for Anthropic services. Defaults to
         *   Anthropic.ANTHROPIC_VERSION.
         * @param ollamaBaseUrl The base URL for Ollama services. Defaults to Ollama.BASE_URL.
         * @param geminiKey The API key for Gemini services.
         * @param geminiBaseUrl The base URL for Gemini services. Defaults to Gemini.BASE_URL.
         * @return An OpenAIGateway instance.
         */
        fun create(
            openAIKey: String = "CONFIGURE_ME",
            openAIBaseUrl: String = OpenAI.BASE_URL,
            anthropicKey: String = "CONFIGURE_ME",
            anthropicBaseUrl: String = Anthropic.BASE_URL,
            anthropicVersion: String = Anthropic.ANTHROPIC_VERSION,
            ollamaBaseUrl: String = Ollama.BASE_URL,
            geminiKey: String = "CONFIGURE_ME",
            geminiBaseUrl: String = Gemini.BASE_URL,
        ): OpenAIGateway =
            initOpenAIGateway(
                openAIConfig =
                    OpenAIProviderConfig.default(
                        baseUrl = { openAIBaseUrl },
                        apiKey = { openAIKey },
                    ),
                anthropicConfig =
                    OpenAIProviderConfig.anthropic(
                        baseUrl = { anthropicBaseUrl },
                        apiKey = { anthropicKey },
                        anthropicVersion = { anthropicVersion },
                    ),
                ollamaConfig = OpenAIProviderConfig.ollama(baseUrl = { ollamaBaseUrl }),
                geminiConfig =
                    OpenAIProviderConfig.gemini(baseUrl = { geminiBaseUrl }, apiKey = { geminiKey }),
            )

        /**
         * Creates an OpenAI Gateway with dynamic configuration. Use this when your API keys or
         * settings may change at runtime.
         *
         * Usage:
         * ```kotlin
         * // Kotlin
         * val gateway = OpenAIGateway.create(
         *     openAIKey = { settings.openAIKey },
         *     anthropicKey = { settings.anthropicKey },
         *     geminiKey = { settings.geminiKey }
         * )
         * ```
         * ```swift
         * // Swift
         * let gateway = OpenAIGateway.create(
         *     openAIKey: { Settings.shared.openAIKey },
         *     anthropicKey: { Settings.shared.anthropicKey },
         *     geminiKey: { Settings.shared.geminiKey }
         * )
         * ```
         */
        fun create(
            openAIKey: () -> String = { "CONFIGURE_ME" },
            openAIBaseUrl: () -> String = { OpenAI.BASE_URL },
            anthropicKey: () -> String = { "CONFIGURE_ME" },
            anthropicBaseUrl: () -> String = { Anthropic.BASE_URL },
            anthropicVersion: () -> String = { Anthropic.ANTHROPIC_VERSION },
            ollamaBaseUrl: () -> String = { Ollama.BASE_URL },
            geminiKey: () -> String = { "CONFIGURE_ME" },
            geminiBaseUrl: () -> String = { Gemini.BASE_URL },
        ): OpenAIGateway =
            initOpenAIGateway(
                openAIConfig =
                    OpenAIProviderConfig.default(baseUrl = openAIBaseUrl, apiKey = openAIKey),
                anthropicConfig =
                    OpenAIProviderConfig.anthropic(
                        baseUrl = anthropicBaseUrl,
                        apiKey = anthropicKey,
                        anthropicVersion = anthropicVersion,
                    ),
                ollamaConfig = OpenAIProviderConfig.ollama(baseUrl = ollamaBaseUrl),
                geminiConfig =
                    OpenAIProviderConfig.gemini(baseUrl = geminiBaseUrl, apiKey = geminiKey),
            )
    }

    fun updateProvider(id: String, name: String, config: OpenAIProviderConfig)

    fun addProvider(provider: OpenAIProvider): OpenAIGateway

    fun removeProvider(id: String)

    fun getProviders(): List<OpenAIProvider>

    /**
     * Creates an image given a prompt. Get images as URLs or base64-encoded JSON.
     *
     * @param request image creation request.
     * @return list of images.
     */
    suspend fun generate(request: ImageCreate, provider: LLMProvider): ListResponse<Image>

    /**
     * Fetch a completion.
     *
     * @param request The request to fetch a completion.
     * @param provider The provider to use for the completion.
     * @return The completion
     */
    suspend fun completions(request: CompletionRequest, provider: LLMProvider): Completion

    /**
     * Fetch a chat completion.
     *
     * @param request The request to fetch a chat completion.
     * @param provider The provider to use for the chat completion.
     * @return The chat completion
     */
    suspend fun chatCompletions(
        request: ChatCompletionRequest,
        provider: LLMProvider,
    ): ChatCompletion

    /**
     * Stream a chat completion.
     *
     * @param request The request to stream a chat completion.
     * @param provider The provider to use for the chat completion.
     * @return The chat completion chunks as a stream
     */
    fun streamChatCompletions(
        request: ChatCompletionRequest,
        provider: LLMProvider,
    ): Flow<ChatCompletionChunk>
}
