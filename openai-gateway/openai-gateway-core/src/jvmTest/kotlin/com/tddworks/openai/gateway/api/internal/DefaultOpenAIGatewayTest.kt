package com.tddworks.openai.gateway.api.internal

import app.cash.turbine.test
import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.azure.api.AzureAIProviderConfig
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.gateway.api.LLMProvider
import com.tddworks.openai.gateway.api.OpenAIProvider
import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

@OptIn(ExperimentalSerializationApi::class)
class DefaultOpenAIGatewayTest {

    private val anthropic = mock<OpenAIProvider> {
        on(it.id).thenReturn("anthropic")
        on(it.name).thenReturn("Anthropic")
    }

    private val ollama = mock<OpenAIProvider> {
        on(it.id).thenReturn("ollama")
        on(it.name).thenReturn("Ollama")
    }

    private val default = mock<OpenAIProvider> {
        on(it.id).thenReturn("default")
        on(it.name).thenReturn("Default")
    }

    private val azure = mock<OpenAIProvider> {
        on(it.id).thenReturn("azure")
        on(it.name).thenReturn("azure")
    }

    private val providers: List<OpenAIProvider> = listOf(
        default,
        anthropic,
        ollama,
        azure
    )

    private val openAIGateway = DefaultOpenAIGateway(
        providers,
    )

    @Test
    fun `should update azure provider`() {
        // Given
        val id = "default"
        val name = "new Default"
        val config = AzureAIProviderConfig(
            apiKey = { "new api key" },
            baseUrl = { "new endpoint" },
            deploymentId = { "new deployment id" },
            apiVersion = { "new api version" }
        )

        // When
        openAIGateway.updateProvider(id, name, config)

        // Then
        assertEquals(4, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
    }

    @Test
    fun `should update openai provider`() {
        // Given
        val id = "default"
        val name = "new Default"
        val config = OpenAIProviderConfig.default(
            baseUrl = { "api.openai.com" },
            apiKey = { "new api key" }
        )

        // When
        openAIGateway.updateProvider(id, name, config)

        // Then
        assertEquals(4, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
    }

    @Test
    fun `should update ollama provider`() {
        // Given
        val id = "ollama"
        val name = "new Ollama"
        val config = OpenAIProviderConfig.ollama()

        // When
        openAIGateway.updateProvider(id, name, config)

        // Then
        assertEquals(4, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
    }

    @Test
    fun `should able to update anthropic provider`() {
        // Given
        val id = "anthropic"
        val name = "new Anthropic"
        val config = OpenAIProviderConfig.anthropic(
            apiKey = { "" },
        )

        // When
        openAIGateway.updateProvider(id, name, config)

        // Then
        assertEquals(4, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
    }

    @Test
    fun `should able to remove provider`() {
        openAIGateway.removeProvider(anthropic.id)
        // Then
        assertEquals(3, openAIGateway.getProviders().size)
        assertEquals(azure, openAIGateway.getProviders().last())
    }

    @Test
    fun `should able to add new provider`() {
        // Given
        val provider = mock<OpenAIProvider>()

        // When
        val gateway = DefaultOpenAIGateway(
            providers,
        ).run {
            addProvider(provider)
        }

        // Then
        assertEquals(5, gateway.getProviders().size)
        assertEquals(provider, gateway.getProviders().last())
    }

    @Test
    fun `should use ollama client to get chat completions`() = runTest {
        // Given
        val model = OllamaModel.LLAMA2
        val response = ChatCompletion.dummy()
        val request = ChatCompletionRequest.dummy(OpenAIModel(model.value))
        whenever(ollama.chatCompletions(request)).thenReturn(response)

        // When
        val r = openAIGateway.chatCompletions(request, LLMProvider.OLLAMA)
        // Then
        assertEquals(response, r)
    }

    @Test
    fun `should use ollama client to get stream completions`() = runTest {
        // Given
        val model = OllamaModel.LLAMA2
        val request = ChatCompletionRequest.dummy(OpenAIModel(model.value))

        val chatCompletionChunk = ChatCompletionChunk.dummy()
        whenever(ollama.streamChatCompletions(request)).thenReturn(flow {
            emit(chatCompletionChunk)
        })

        // When
        openAIGateway.streamChatCompletions(request, LLMProvider.OLLAMA).test {
            // Then
            assertEquals(
                chatCompletionChunk, awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `should get exception No provider found when client use model not exist to get stream completions`() =
        runTest {
            // Given
            val model = OpenAIModel.GPT_3_5_TURBO
            val request = ChatCompletionRequest.dummy(model)

            // When
            assertThrows<UnsupportedOperationException> {
                openAIGateway.streamChatCompletions(request, LLMProvider.OLLAMA)
            }
        }

    @Test
    fun `should use anthropic client to get stream completions`() = runTest {
        // Given
        val model = AnthropicModel.CLAUDE_3_HAIKU
        val request = ChatCompletionRequest.dummy(OpenAIModel(model.value))

        val chatCompletionChunk = ChatCompletionChunk.dummy()
        whenever(anthropic.streamChatCompletions(request)).thenReturn(flow {
            emit(chatCompletionChunk)
        })

        // When
        openAIGateway.streamChatCompletions(request, LLMProvider.ANTHROPIC).test {
            // Then
            assertEquals(
                chatCompletionChunk, awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `should use anthropic client to get chat completions`() = runTest {
        // Given
        val model = AnthropicModel.CLAUDE_3_HAIKU
        val response = ChatCompletion.dummy()
        val request = ChatCompletionRequest.dummy(OpenAIModel(model.value))
        whenever(anthropic.chatCompletions(request)).thenReturn(response)

        // When
        val r = openAIGateway.chatCompletions(request, LLMProvider.ANTHROPIC)
        // Then
        assertEquals(response, r)
    }
}