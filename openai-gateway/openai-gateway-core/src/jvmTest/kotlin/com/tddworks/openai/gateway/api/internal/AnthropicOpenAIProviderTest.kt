package com.tddworks.openai.gateway.api.internal

import app.cash.turbine.test
import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import com.tddworks.openai.gateway.api.OpenAIProvider
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class AnthropicOpenAIProviderTest {
    private lateinit var client: Anthropic
    private lateinit var config: AnthropicOpenAIProviderConfig

    private lateinit var provider: OpenAIProvider

    @BeforeEach
    fun setUp() {
        client = mock()
        config = AnthropicOpenAIProviderConfig(apiKey = { "" })
        provider = OpenAIProvider.anthropic(client = client, config = config)
    }

    @Test
    fun `should throw not supported when invoke completions`() = runTest {
        // given
        val request =
            CompletionRequest(
                prompt = "Once upon a time",
                suffix = "The end",
                maxTokens = 10,
                temperature = 0.5,
            )

        runCatching {
                // when
                provider.completions(request)
            }
            .onFailure {
                // then
                assertEquals("Not supported", it.message)
            }
    }

    @Test
    fun `should fetch completions from OpenAI API`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(AnthropicModel.CLAUDE_3_HAIKU.value))
        val response =
            CreateMessageResponse(
                id = "msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY",
                type = "message",
                role = "assistant",
                content = emptyList(),
                model = "claude-3-opus-20240229",
                stopReason = null,
                stopSequence = null,
                usage = Usage(inputTokens = 25, outputTokens = 1),
            )
        whenever(client.create(request.toAnthropicRequest())).thenReturn(response)

        // when
        val completions = provider.chatCompletions(request)

        // then
        assertEquals(response.toOpenAIChatCompletion(), completions)
    }

    @Test
    fun `should stream completions for chat`() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(AnthropicModel.CLAUDE_3_HAIKU.value))

        val contentBlockStart =
            ContentBlockStart(
                type = "content_block_start",
                index = 0,
                contentBlock = ContentBlock(type = "some-type", text = "som-text"),
            )
        whenever(client.stream(request.toAnthropicStreamRequest()))
            .thenReturn(flow { emit(contentBlockStart) })

        // when
        provider.streamChatCompletions(request).test {
            // then
            assertEquals(
                contentBlockStart.toOpenAIChatCompletionChunk(AnthropicModel.CLAUDE_3_HAIKU.value),
                awaitItem(),
            )
            awaitComplete()
        }
    }
}
