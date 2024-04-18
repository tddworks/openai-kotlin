package com.tddworks.openai.gateway.api

import app.cash.turbine.test
import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.anthropic.api.Model
import com.tddworks.di.getInstance
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.gateway.di.initOpenAIGateway
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.koin.test.junit5.AutoCloseKoinTest
import kotlin.test.assertNotNull
import com.tddworks.openai.api.chat.api.ChatCompletionRequest as OpenAIChatCompletionRequest
import com.tddworks.openai.api.chat.api.Model as OpenAIModel

/**
 * Integration test for the OpenAI Gateway.
 * This test requires the following environment variables to be set:
 * - OPENAI_API_KEY
 * - ANTHROPIC_API_KEY
 */
@ExperimentalSerializationApi
class OpenAIGatewayITest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        initOpenAIGateway(
            openAIConfig = OpenAIConfig(
                baseUrl = { "api.openai.com" },
                apiKey = { System.getenv("OPENAI_API_KEY") ?: "" }
            ),
            anthropicConfig = AnthropicConfig(
                baseUrl = { "api.anthropic.com" },
                apiKey = { System.getenv("ANTHROPIC_API_KEY") ?: "" },
                anthropicVersion = { "2023-06-01" }
            ),
            ollamaConfig = OllamaConfig()
        )
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "OLLAMA_STARTED", matches = "true")
    fun `should use ollama client to get chat completions`() = runTest {
        val gateway = getInstance<OpenAIGateway>()
        gateway.streamCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                model = OpenAIModel(OllamaModel.LLAMA2.value)
            )
        ).test {
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "OPENAI_API_KEY", matches = ".+")
    fun `should use openai client to get chat completions`() = runTest {
        val gateway = getInstance<OpenAIGateway>()
        gateway.streamCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                model = OpenAIModel.GPT_3_5_TURBO
            )
        ).test {
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "ANTHROPIC_API_KEY", matches = ".+")
    fun `should use anthropic client to get chat completions`() = runTest {
        val gateway = getInstance<OpenAIGateway>()
        gateway.streamCompletions(
            OpenAIChatCompletionRequest(
                messages = listOf(ChatMessage.UserMessage("hello")),
                maxTokens = 1024,
                model = OpenAIModel(Model.CLAUDE_3_HAIKU.value)
            )
        ).test {
            assertNotNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}