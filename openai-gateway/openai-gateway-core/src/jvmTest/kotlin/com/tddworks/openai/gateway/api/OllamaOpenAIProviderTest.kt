package com.tddworks.openai.gateway.api

import app.cash.turbine.test
import com.tddworks.ollama.api.Ollama
import com.tddworks.ollama.api.OllamaModel
import com.tddworks.ollama.api.chat.OllamaChatResponse
import com.tddworks.ollama.api.chat.api.toOllamaChatRequest
import com.tddworks.ollama.api.chat.api.toOpenAIChatCompletion
import com.tddworks.ollama.api.chat.api.toOpenAIChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.Model
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExperimentalSerializationApi
@ExtendWith(MockitoExtension::class)
class OllamaOpenAIProviderTest {
    @Mock
    lateinit var client: Ollama

    @InjectMocks
    lateinit var provider: OllamaOpenAIProvider

    @Test
    fun `should return true when model is supported`() {
        // given
        val supportedModel = Model(OllamaModel.LLAMA2.value)

        // when
        val isSupported = provider.supports(supportedModel)

        // then
        kotlin.test.assertTrue(isSupported)
    }

    @Test
    fun `should return false when model is not supported`() {
        // given
        val unsupportedModel = Model.GPT_3_5_TURBO

        // when
        val isSupported = provider.supports(unsupportedModel)

        // then
        kotlin.test.assertFalse(isSupported)
    }

    @Test
    fun `should fetch completions from OpenAI API`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(Model(OllamaModel.LLAMA2.value))
        val response = OllamaChatResponse.dummy()
        whenever(client.request(request.toOllamaChatRequest())).thenReturn(response)

        // when
        val completions = provider.completions(request)

        // then
        assertEquals(response.toOpenAIChatCompletion(), completions)
    }

    @Test
    fun `should stream completions for chat`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(Model(OllamaModel.LLAMA2.value))

        val response = OllamaChatResponse.dummy()
        whenever(client.stream(request.toOllamaChatRequest())).thenReturn(flow {
            emit(
                response
            )
        })

        // when
        provider.streamCompletions(request).test {
            // then
            assertEquals(
                response.toOpenAIChatCompletionChunk(),
                awaitItem()
            )
            awaitComplete()
        }

    }
}