package com.tddworks.openai.gateway.api

import app.cash.turbine.test
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.Model
import com.tddworks.openai.api.legacy.completions.api.Completion
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.internal.DefaultOpenAIGateway
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import com.tddworks.anthropic.api.Model as AnthropicModel

@OptIn(ExperimentalSerializationApi::class)
class DefaultOpenAIGatewayTest {
    private val anthropic = mock<OpenAIProvider> {
        on(it.supports(Model(AnthropicModel.CLAUDE_3_HAIKU.value))).thenReturn(true)
        on(it.name).thenReturn("Anthropic")
    }

    private val ollama = mock<OpenAIProvider> {
        on(it.supports(Model(OllamaModel.LLAMA2.value))).thenReturn(true)
        on(it.name).thenReturn("Ollama")
    }

    private val providers: List<OpenAIProvider> = listOf(
        anthropic,
        ollama
    )

    private val openAIGateway = DefaultOpenAIGateway(
        providers,
    )

    @Test
    fun `should able to remove provider`() {
        openAIGateway.removeProvider(anthropic.name)
        // Then
        assertEquals(1, openAIGateway.getProviders().size)
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
        assertEquals(3, gateway.getProviders().size)
        assertEquals(provider, gateway.getProviders().last())
    }

    @Test
    fun `should use ollama client to get chat completions`() = runTest {
        // Given
        val model = OllamaModel.LLAMA2
        val response = ChatCompletion.dummy()
        val request = ChatCompletionRequest.dummy(Model(model.value))
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
        val request = ChatCompletionRequest.dummy(Model(model.value))

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
            val model = Model.GPT_3_5_TURBO
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
        val request = ChatCompletionRequest.dummy(Model(model.value))

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
        val request = ChatCompletionRequest.dummy(Model(model.value))
        whenever(anthropic.chatCompletions(request)).thenReturn(response)

        // When
        val r = openAIGateway.chatCompletions(request)
        // Then
        assertEquals(response, r)
    }
}