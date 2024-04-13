package com.tddworks.anthropic.api.messages.api

import com.tddworks.openai.api.chat.api.ChatCompletionRequest
import com.tddworks.anthropic.api.Model
import com.tddworks.openai.api.chat.api.ChatMessage
import com.tddworks.openai.api.chat.api.Model as OpenAIModel
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExtensionsTest {

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
        val chatCompletionRequest = ChatCompletionRequest.dummy(OpenAIModel(Model.CLAUDE_3_HAIKU.value))

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