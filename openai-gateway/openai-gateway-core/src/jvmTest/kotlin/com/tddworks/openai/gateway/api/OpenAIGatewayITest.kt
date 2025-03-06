package com.tddworks.openai.gateway.api

import app.cash.turbine.test
import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.gemini.api.textGeneration.api.GeminiModel
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.gateway.api.internal.anthropic
import com.tddworks.openai.gateway.api.internal.default
import com.tddworks.openai.gateway.api.internal.gemini
import com.tddworks.openai.gateway.api.internal.ollama
import com.tddworks.openai.gateway.di.initOpenAIGateway
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertNotNull
import kotlin.time.Duration.Companion.seconds
import com.tddworks.openai.api.chat.api.ChatCompletionRequest as OpenAIChatCompletionRequest

/**
 * Integration test for the OpenAI Gateway.
 * This test requires the following environment variables to be set:
 * - OPENAI_API_KEY
 * - ANTHROPIC_API_KEY
 */
@ExperimentalSerializationApi
class OpenAIGatewayITest : AutoCloseKoinTest() {

    private lateinit var gateway: OpenAIGateway

    @BeforeEach
    fun setUp() {
        gateway = initOpenAIGateway(
            openAIConfig = OpenAIProviderConfig.default(
                baseUrl = { "api.openai.com" },
                apiKey = { System.getenv("OPENAI_API_KEY") ?: "CONFIGURE_ME" }
            ),
            anthropicConfig = OpenAIProviderConfig.anthropic(
                baseUrl = { "api.anthropic.com" },
                apiKey = { System.getenv("ANTHROPIC_API_KEY") ?: "CONFIGURE_ME" },
                anthropicVersion = { "2023-06-01" }
            ),
            ollamaConfig = OpenAIProviderConfig.ollama(),
            geminiConfig = OpenAIProviderConfig.gemini(
                baseUrl = { Gemini.BASE_URL },
                apiKey = { System.getenv("GEMINI_API_KEY") ?: "CONFIGURE_ME" }
            )
        )
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "GEMINI_API_KEY", matches = ".+")
    fun `should use gemini client to get chat completions`() = runTest {
        gateway.streamChatCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                model = OpenAIModel(GeminiModel.GEMINI_1_5_FLASH.value),
            ),
            LLMProvider.GEMINI
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "OLLAMA_STARTED", matches = "true")
    fun `should use ollama client to get chat completions`() = runTest {
        gateway.streamChatCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                model = OpenAIModel(OllamaModel.LLAMA3.value)
            ),
            LLMProvider.OLLAMA
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
    fun `should use openai client to get chat completions`() = runTest {
        gateway.streamChatCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                model = OpenAIModel.GPT_3_5_TURBO
            ),
            LLMProvider.OPENAI
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".+")
    fun `should use anthropic client to get chat completions`() = runTest {
        gateway.streamChatCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                model = OpenAIModel(AnthropicModel.CLAUDE_3_HAIKU.value)
            ),
            LLMProvider.ANTHROPIC
        ).test(timeout = 10.seconds) {
            assertNotNull(awaitItem())
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}