package com.tddworks.openai.gateway.api.internal

import app.cash.turbine.test
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
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
import com.tddworks.anthropic.api.AnthropicModel as AnthropicModel

@OptIn(ExperimentalSerializationApi::class)
class DefaultOpenAIGatewayTest {
    private val anthropic = mock<OpenAIProvider> {
        on(it.id).thenReturn("anthropic")
        on(it.supports(OpenAIModel(AnthropicModel.CLAUDE_3_HAIKU.value))).thenReturn(true)
        on(it.name).thenReturn("Anthropic")
    }

    private val ollama = mock<OpenAIProvider> {
        on(it.id).thenReturn("ollama")
        on(it.supports(OpenAIModel(OllamaModel.LLAMA2.value))).thenReturn(true)
        on(it.name).thenReturn("Ollama")
    }

    private val default = mock<OpenAIProvider> {
        on(it.id).thenReturn("default")
        on(it.supports(OpenAIModel(AnthropicModel.CLAUDE_3_HAIKU.value))).thenReturn(false)
        on(it.supports(OpenAIModel(OllamaModel.LLAMA2.value))).thenReturn(false)
        on(it.name).thenReturn("Default")
    }

    private val providers: List<OpenAIProvider> = listOf(
        default,
        anthropic,
        ollama
    )

    private val openAIGateway = DefaultOpenAIGateway(
        providers,
    )

    @Test
    fun `should update openai provider`() {
        // Given
        val id = "default"
        val name = "new Default"
        val config = OpenAIProviderConfig.default(
            baseUrl = { "api.openai.com" },
            apiKey = { "new api key" }
        )

        val models = listOf(OpenAIModel(AnthropicModel.CLAUDE_3_HAIKU.value))

        // When
        openAIGateway.updateProvider(id, name, config, models)

        // Then
        assertEquals(3, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
        assertEquals(models, openAIProvider.models)
    }

    @Test
    fun `should update ollama provider`() {
        // Given
        val id = "ollama"
        val name = "new Ollama"
        val config = OpenAIProviderConfig.ollama()

        val models = listOf(OpenAIModel(OllamaModel.LLAMA2.value))

        // When
        openAIGateway.updateProvider(id, name, config, models)

        // Then
        assertEquals(3, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
        assertEquals(models, openAIProvider.models)
    }

    @Test
    fun `should able to update anthropic provider`() {
        // Given
        val id = "anthropic"
        val name = "new Anthropic"
        val config = OpenAIProviderConfig.anthropic(
            apiKey = { "" },
        )

        val models = listOf(OpenAIModel.GPT_3_5_TURBO)

        // When
        openAIGateway.updateProvider(id, name, config, models)

        // Then
        assertEquals(3, openAIGateway.getProviders().size)
        val openAIProvider = openAIGateway.getProviders().first { it.id == id }
        assertEquals(name, openAIProvider.name)
        assertEquals(config, openAIProvider.config)
        assertEquals(models, openAIProvider.models)
    }

    @Test
    fun `should able to remove provider`() {
        openAIGateway.removeProvider(anthropic.name)
        // Then
        assertEquals(2, openAIGateway.getProviders().size)
        assertEquals(ollama, openAIGateway.getProviders().last())
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
        assertEquals(4, gateway.getProviders().size)
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
        val r = openAIGateway.chatCompletions(request)
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
        openAIGateway.streamChatCompletions(request).test {
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
                openAIGateway.streamChatCompletions(request)
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
        openAIGateway.streamChatCompletions(request).test {
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
        val r = openAIGateway.chatCompletions(request)
        // Then
        assertEquals(response, r)
    }
}