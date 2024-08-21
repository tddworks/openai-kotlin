package com.tddworks.anthropic.api.messages.api

import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.anthropic.api.Model
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.Role.Companion.Assistant
import com.tddworks.openai.api.chat.api.OpenAIModel as OpenAIModel
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExtensionsTest {

    @Test
    fun `should return Stop when finishReason is end_turn`() {
        val stopReason = mapAnthropicStopReason("end_turn")
        assertEquals(OpenAIStopReason.Stop, stopReason)
    }

    @Test
    fun `should return Stop when finishReason is stop_sequence`() {
        val stopReason = mapAnthropicStopReason("stop_sequence")
        assertEquals(OpenAIStopReason.Stop, stopReason)
    }

    @Test
    fun `should return Stop when finishReason is message_stop`() {
        val stopReason = mapAnthropicStopReason("message_stop")
        assertEquals(OpenAIStopReason.Stop, stopReason)
    }

    @Test
    fun `should return ToolCalls when finishReason is tool_use`() {
        val stopReason = mapAnthropicStopReason("tool_use")
        assertEquals(OpenAIStopReason.ToolCalls, stopReason)
    }

    @Test
    fun `should return Length when finishReason is max_tokens`() {
        val stopReason = mapAnthropicStopReason("max_tokens")
        assertEquals(OpenAIStopReason.Length, stopReason)
    }

    @Test
    fun `should return Other when finishReason is not recognized`() {
        val stopReason = mapAnthropicStopReason("some_reason")
        assertEquals(OpenAIStopReason.Other, stopReason)
    }

    @Test
    fun `should convert to open chat completion when create message response`() {
        // Given
        val createMessageResponse = CreateMessageResponse.dummy()

        // When
        val result = createMessageResponse.toOpenAIChatCompletion()

        // Then
        assertEquals("msg_1nZdL29xx5MUA1yADyHTEsnR8uuvGzszyY", result.id)
        assertEquals(1L, result.created)
        assertEquals("claude-3-opus-20240229", result.model)
        assertEquals(1, result.choices.size)
        assertEquals("Hi! My name is Claude.", result.choices[0].message.content)
        assertEquals(Assistant, result.choices[0].message.role)
        assertEquals(0, result.choices[0].index)
    }

    @Test
    fun `should convert to finish reason null when content block delta`() {
        // Given
        val response = ContentBlockDelta(
            type = "content_block_delta",
            index = 0,
            delta = Delta(
                type = "text_delta",
                text = "Hello"
            )
        )

        val model = "gpt-3.5-turbo-0125"

        // When
        val chatCompletionChunk = response.toOpenAIChatCompletionChunk(model)

        // Then
        assertEquals("chatcmpl-123", chatCompletionChunk.id)
        assertEquals("content_block_delta", chatCompletionChunk.`object`)
        assertEquals(1L, chatCompletionChunk.created)
        assertEquals(model, chatCompletionChunk.model)
        assertEquals(1, chatCompletionChunk.choices.size)
        assertEquals(0, chatCompletionChunk.choices[0].index)
        assertEquals(null, chatCompletionChunk.choices[0].finishReason)
    }

    @Test
    fun `should convert to finish reason null when content block start`() {
        // Given
        val response = ContentBlockStart(
            type = "content_block_start",
            index = 0,
            contentBlock = ContentBlock(
                type = "text",
                text = ""
            )

        )
        val model = "gpt-3.5-turbo-0125"

        // When
        val chatCompletionChunk = response.toOpenAIChatCompletionChunk(model)

        // Then
        assertEquals("chatcmpl-123", chatCompletionChunk.id)
        assertEquals("content_block_start", chatCompletionChunk.`object`)
        assertEquals(1L, chatCompletionChunk.created)
        assertEquals(model, chatCompletionChunk.model)
        assertEquals(1, chatCompletionChunk.choices.size)
        assertEquals(0, chatCompletionChunk.choices[0].index)
        assertEquals(null, chatCompletionChunk.choices[0].finishReason)
    }

    @Test
    fun `should convert to open chat completion chunk when message delta`() {
        // Given
        val response = MessageDelta(
            type = "chat.completion.chunk",
            delta = Delta.dummy()
        )
        val model = "gpt-3.5-turbo-0125"

        // When
        val chatCompletionChunk = response.toOpenAIChatCompletionChunk(model)

        // Then
        assertEquals("chatcmpl-123", chatCompletionChunk.id)
        assertEquals("chat.completion.chunk", chatCompletionChunk.`object`)
        assertEquals(1L, chatCompletionChunk.created)
        assertEquals(model, chatCompletionChunk.model)
        assertEquals(1, chatCompletionChunk.choices.size)
        assertEquals(0, chatCompletionChunk.choices[0].index)
        assertEquals("Stop", chatCompletionChunk.choices[0].finishReason)
    }

    @Test
    fun `should convert assistantMessage to anthropic stream request`() {
        // Given
        val chatCompletionRequest = ChatCompletionRequest(
            listOf(ChatMessage.AssistantMessage("Hello! How can I assist you today?")),
            model = OpenAIModel(Model.CLAUDE_3_HAIKU.value)
        )

        // When
        val anthropicRequest = chatCompletionRequest.toAnthropicStreamRequest()

        // Then
        assertEquals(
            """
            {
              "messages": [
                {
                  "role": "assistant",
                  "content": "Hello! How can I assist you today?"
                }
              ],
              "stream": true
            }
        """.trimIndent(),
            prettyJson.encodeToString(CreateMessageRequest.serializer(), anthropicRequest)
        )
    }

    @Test
    fun `should convert assistantMessage to anthropic request`() {
        // Given
        val chatCompletionRequest = ChatCompletionRequest(
            listOf(ChatMessage.AssistantMessage("Hello! How can I assist you today?")),
            model = OpenAIModel(Model.CLAUDE_3_HAIKU.value)
        )

        // When
        val anthropicRequest = chatCompletionRequest.toAnthropicRequest()

        // Then
        assertEquals(
            """
            {
              "messages": [
                {
                  "role": "assistant",
                  "content": "Hello! How can I assist you today?"
                }
              ]
            }
        """.trimIndent(),
            prettyJson.encodeToString(CreateMessageRequest.serializer(), anthropicRequest)
        )
    }

    @Test
    fun `should convert user to anthropic request`() {
        // Given
        val chatCompletionRequest =
            ChatCompletionRequest.dummy(OpenAIModel(Model.CLAUDE_3_HAIKU.value))

        // When
        val anthropicRequest = chatCompletionRequest.toAnthropicRequest()

        // Then
        assertEquals(
            """
            {
              "messages": [
                {
                  "role": "user",
                  "content": "Hello! How can I assist you today?"
                }
              ]
            }
        """.trimIndent(),
            prettyJson.encodeToString(CreateMessageRequest.serializer(), anthropicRequest)
        )
    }

    val prettyJson = Json { // this returns the JsonBuilder
        prettyPrint = true
        ignoreUnknownKeys = true
        // optional: specify indent
        prettyPrintIndent = "  "
    }

}