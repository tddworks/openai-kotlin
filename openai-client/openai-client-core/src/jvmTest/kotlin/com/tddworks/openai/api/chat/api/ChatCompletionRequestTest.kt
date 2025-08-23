package com.tddworks.openai.api.chat.api

import com.tddworks.openai.api.common.prettyJson
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

@OptIn(ExperimentalSerializationApi::class)
class ChatCompletionRequestTest {

    @Test
    fun `should return to correct stream json`() {
        val chatCompletionRequest =
            ChatCompletionRequest(messages = listOf(ChatMessage.user("hello")), stream = true)

        val result = prettyJson.encodeToString(chatCompletionRequest)

        assertEquals(
            """
            {
              "messages": [
                {
                  "content": "hello",
                  "role": "user"
                }
              ],
              "model": "gpt-3.5-turbo",
              "stream": true
            }
            """
                .trimIndent(),
            result,
        )
    }

    @Test
    fun `should return to correct json`() {
        val chatCompletionRequest =
            ChatCompletionRequest(messages = listOf(ChatMessage.user("hello")))

        val result =
            prettyJson.encodeToString(ChatCompletionRequest.serializer(), chatCompletionRequest)

        assertEquals(
            """
            {
              "messages": [
                {
                  "content": "hello",
                  "role": "user"
                }
              ],
              "model": "gpt-3.5-turbo"
            }
            """
                .trimIndent(),
            result,
        )
    }
}
