package com.tddworks.openai.api.chat

import com.tddworks.openai.api.prettyJson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ChatChoiceTest {

    @Test
    fun `should return chat choice from json`() {
        val json = """
            {
              "index": 0,
              "message": {
                "role": "assistant",
                "content": "Hello! How can I assist you today?"
              },
              "logprobs": null,
              "finish_reason": "stop"
            }
        """.trimIndent()

        val chatChoice = prettyJson.decodeFromString(ChatChoice.serializer(), json)

        with(chatChoice) {
            assertEquals(0, index)
            assertEquals("Hello! How can I assist you today?", message.content)
            assertEquals("ASSISTANT", message.role.name)
            assertEquals("stop", finishReason?.value)
        }
    }
}