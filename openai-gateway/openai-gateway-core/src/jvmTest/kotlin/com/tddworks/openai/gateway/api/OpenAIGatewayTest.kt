package com.tddworks.openai.gateway.api

import app.cash.turbine.test
import com.tddworks.openai.api.chat.api.ChatCompletion
import com.tddworks.openai.api.chat.api.ChatCompletionChunk
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.Model
import com.tddworks.openai.gateway.api.internal.DefaultOpenAIGateway
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import com.tddworks.anthropic.api.Model as AnthropicModel

class OpenAIGatewayTest {

    private val openAI = mock<OpenAIProvider> {
        on(it.supports(Model.GPT_3_5_TURBO)).thenReturn(true)
    }
    private val anthropic = mock<OpenAIProvider> {
        on(it.supports(Model(AnthropicModel.CLAUDE_3_HAIKU.value))).thenReturn(true)
    }
    private val providers: List<OpenAIProvider> = listOf(
        openAI,
        anthropic
    )

    private val openAIGateway = DefaultOpenAIGateway(providers)

    @Test
    fun `should use openai client to get stream completions`() = runTest {
        // Given
        val model = Model.GPT_3_5_TURBO
        val request = ChatCompletionRequest.dummy(model)

        val chatCompletionChunk = ChatCompletionChunk.dummy()
        whenever(openAI.streamCompletions(request)).thenReturn(flow {
            emit(chatCompletionChunk)
        })

        // When
        openAIGateway.streamCompletions(request).test {
            // Then
            assertEquals(
                chatCompletionChunk, awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `should use anthropic client to get stream completions`() = runTest {
        // Given
        val model = AnthropicModel.CLAUDE_3_HAIKU
        val request = ChatCompletionRequest.dummy(Model(model.value))

        val chatCompletionChunk = ChatCompletionChunk.dummy()
        whenever(anthropic.streamCompletions(request)).thenReturn(flow {
            emit(chatCompletionChunk)
        })

        // When
        openAIGateway.streamCompletions(request).test {
            // Then
            assertEquals(
                chatCompletionChunk, awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `should use openai client to get chat completions`() = runTest {
        // Given
        val model = Model.GPT_3_5_TURBO
        val response = ChatCompletion.dummy()
        val request = ChatCompletionRequest.dummy(model)
        whenever(openAI.completions(request)).thenReturn(response)

        // When
        val r = openAIGateway.completions(request)
        // Then
        assertEquals(response, r)
    }


    @Test
    fun `should use anthropic client to get chat completions`() = runTest {
        // Given
        val model = AnthropicModel.CLAUDE_3_HAIKU
        val response = ChatCompletion.dummy()
        val request = ChatCompletionRequest.dummy(Model(model.value))
        whenever(anthropic.completions(request)).thenReturn(response)

        // When
        val r = openAIGateway.completions(request)
        // Then
        assertEquals(response, r)
    }
}