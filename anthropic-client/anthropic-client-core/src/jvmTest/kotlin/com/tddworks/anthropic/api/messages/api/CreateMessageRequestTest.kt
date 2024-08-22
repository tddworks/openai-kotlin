package com.tddworks.anthropic.api.messages.api

import com.tddworks.anthropic.api.AnthropicModel
import com.tddworks.anthropic.api.prettyJson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CreateMessageRequestTest {
    @Test
    fun `should convert request to correct json`() {
        val json = """
            {
              "messages": [
                {
                  "role": "user",
                  "content": "hello"
                }
              ],
              "system": null,
              "max_tokens": 1024,
              "model": "claude-3-haiku-20240307",
              "stream": null
            }
        """.trimIndent()

        val request = CreateMessageRequest(
            messages = listOf(Message.user("hello")),
            maxTokens = 1024,
            model = AnthropicModel.CLAUDE_3_HAIKU
        )

        assertEquals(json, prettyJson.encodeToString(CreateMessageRequest.serializer(), request))
    }
}