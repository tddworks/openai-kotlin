package com.tddworks.openai.gateway.api.internal

import app.cash.turbine.test
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.ollama.api.chat.api.*
import com.tddworks.ollama.api.generate.OllamaGenerateResponse
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalSerializationApi
class OllamaOpenAIProviderTest {
    private lateinit var client: Ollama
    private lateinit var config: OllamaOpenAIProviderConfig

    private lateinit var provider: OpenAIProvider

    @BeforeEach
    fun setUp() {
        client = mock()
        config = mock()
        provider = OpenAIProvider.ollama(client = client, config = config)
    }

    @Test
    fun `should return completions response from OpenAI API`() = runTest {
        // given
        val request = CompletionRequest(
            prompt = "Once upon a time",
            suffix = "The end",
            maxTokens = 10,
            temperature = 0.5
        )
        val response = OllamaGenerateResponse.dummy()
        whenever(client.request(request.toOllamaGenerateRequest())).thenReturn(response)

        // when
        val completions = provider.completions(request)

        // then
        assertEquals(response.toOpenAICompletion(), completions)
    }

    @Test
    fun `should return chat completions response from OpenAI API`() = runTest {
        // given
        val request = CompletionRequest(
            prompt = "Once upon a time",
            suffix = "The end",
            maxTokens = 10,
            temperature = 0.5
        )
        val response = OllamaGenerateResponse.dummy()
        whenever(client.request(request.toOllamaGenerateRequest())).thenReturn(response)

        // when
        val completions = provider.completions(request)

        // then
        assertEquals(response.toOpenAICompletion(), completions)
    }

    @Test
    fun `should return true when model is supported`() {
        // given
        val supportedModel = OpenAIModel(OllamaModel.LLAMA2.value)

        // when
        val isSupported = provider.supports(supportedModel)

        // then
        kotlin.test.assertTrue(isSupported)
    }

    @Test
    fun `should return false when model is not supported`() {
        // given
        val unsupportedModel = OpenAIModel.GPT_3_5_TURBO

        // when
        val isSupported = provider.supports(unsupportedModel)

        // then
        kotlin.test.assertFalse(isSupported)
    }

    @Test
    fun `should fetch chat completions from OpenAI API`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(OllamaModel.LLAMA2.value))
        val response = OllamaChatResponse.dummy()
        whenever(client.request(request.toOllamaChatRequest())).thenReturn(response)

        // when
        val completions = provider.chatCompletions(request)

        // then
        assertEquals(response.toOpenAIChatCompletion(), completions)
    }

    @Test
    fun `should stream chat completions for chat`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(OllamaModel.LLAMA2.value))

        val response = OllamaChatResponse.dummy()
        whenever(client.stream(request.toOllamaChatRequest())).thenReturn(flow {
            emit(
                response
            )
        })

        // when
        provider.streamChatCompletions(request).test {
            // then
            assertEquals(
                response.toOpenAIChatCompletionChunk(),
                awaitItem()
            )
            awaitComplete()
        }

    }
}