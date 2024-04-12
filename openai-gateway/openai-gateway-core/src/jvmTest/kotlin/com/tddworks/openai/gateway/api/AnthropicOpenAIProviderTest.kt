package com.tddworks.openai.gateway.api

import app.cash.turbine.test
import com.tddworks.anthropic.api.Anthropic
import com.tddworks.anthropic.api.Model
import com.tddworks.anthropic.api.messages.api.*
import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import com.tddworks.openai.api.chat.api.Model as OpenAIModel

@ExtendWith(MockitoExtension::class)
class AnthropicOpenAIProviderTest {
    @Mock
    lateinit var client: Anthropic

    @InjectMocks
    lateinit var provider: AnthropicOpenAIProvider

    @Test
    fun should_return_true_when_model_is_supported() {
        // given
        val supportedModel = OpenAIModel(Model.CLAUDE_3_HAIKU.value)

        // when
        val isSupported = provider.supports(supportedModel)

        // then
        assertTrue(isSupported)
    }

    @Test
    fun should_return_false_when_model_is_not_supported() {
        // given
        val unsupportedModel = OpenAIModel.GPT_3_5_TURBO

        // when
        val isSupported = provider.supports(unsupportedModel)

        // then
        assertFalse(isSupported)
    }

    @Test
    fun should_fetch_completions_from_OpenAI_API() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(Model.CLAUDE_3_HAIKU.value))
        val response = CreateMessageResponse(
            id = "msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY",
            type = "message",
            role = "assistant",
            content = emptyList(),
            model = "claude-3-opus-20240229",
            stopReason = null,
            stopSequence = null,
            usage = Usage(
                inputTokens = 25,
                outputTokens = 1
            )
        )
        whenever(client.create(request.toAnthropicRequest())).thenReturn(response)

        // when
        val completions = provider.completions(request)

        // then
        assertEquals(response.toOpenAIChatCompletion(), completions)
    }

    @Test
    fun should_stream_completions_for_chat() = runTest {
        // given
        val request = ChatCompletionRequest.dummy(OpenAIModel(Model.CLAUDE_3_HAIKU.value))

        val contentBlockStart = ContentBlockStart(
            type = "content_block_start",
            index = 0,
            contentBlock = ContentBlock(type = "some-type", text = "som-text")
        )
        whenever(client.stream(request.toAnthropicRequest() as StreamMessageRequest)).thenReturn(flow {
            emit(
                contentBlockStart
            )
        })

        // when
        provider.streamCompletions(request).test {
            // then
            assertEquals(contentBlockStart.toOpenAIChatCompletionChunk(Model.CLAUDE_3_HAIKU.value), awaitItem())
            awaitComplete()
        }

    }
}