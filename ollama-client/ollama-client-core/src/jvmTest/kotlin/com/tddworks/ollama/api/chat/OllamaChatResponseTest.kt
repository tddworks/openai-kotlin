package com.tddworks.ollama.api.chat

import com.tddworks.ollama.api.json.JsonLenient
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class OllamaChatResponseTest {

    @Test
    fun `should decode response to non-streaming OllamaChatResponse`() {
        val response =
            """
            {
              "model": "registry.ollama.ai/library/llama2:latest",
              "created_at": "2023-12-12T14:13:43.416799Z",
              "message": {
                "role": "assistant",
                "content": "Hello! How are you today?"
              },
              "done": true,
              "total_duration": 5191566416,
              "load_duration": 2154458,
              "prompt_eval_count": 26,
              "prompt_eval_duration": 383809000,
              "eval_count": 298,
              "eval_duration": 4799921000
            }
            """
                .trimIndent()

        JsonLenient.decodeFromString<OllamaChatResponse>(response).apply {
            assertEquals("registry.ollama.ai/library/llama2:latest", model)
            assertEquals("2023-12-12T14:13:43.416799Z", createdAt)
            assertEquals("assistant", message?.role)
            assertEquals("Hello! How are you today?", message?.content)
            assertTrue(done)
            assertEquals(5191566416, totalDuration)
            assertEquals(2154458, loadDuration)
            assertEquals(26, promptEvalCount)
            assertEquals(383809000, promptEvalDuration)
            assertEquals(298, evalCount)
            assertEquals(4799921000, evalDuration)
        }
    }

    @Test
    fun `should decode response to Final OllamaChatResponse`() {
        val response =
            """
            {
              "model": "llama2",
              "created_at": "2023-08-04T19:22:45.499127Z",
              "done": true,
              "total_duration": 4883583458,
              "load_duration": 1334875,
              "prompt_eval_count": 26,
              "prompt_eval_duration": 342546000,
              "eval_count": 282,
              "eval_duration": 4535599000
            }
            """
                .trimIndent()

        JsonLenient.decodeFromString<OllamaChatResponse>(response).apply {
            assertEquals("llama2", model)
            assertEquals("2023-08-04T19:22:45.499127Z", createdAt)
            assertTrue(done)
            assertEquals(4883583458, totalDuration)
            assertEquals(1334875, loadDuration)
            assertEquals(26, promptEvalCount)
            assertEquals(342546000, promptEvalDuration)
            assertEquals(282, evalCount)
            assertEquals(4535599000, evalDuration)
        }
    }

    @Test
    fun `should decode response to OllamaChatResponse`() {
        val response =
            """
            {
              "model": "llama2",
              "created_at": "2023-08-04T08:52:19.385406455-07:00",
              "message": {
                "role": "assistant",
                "content": "The",
                "images": null
              },
              "done": false
            }
            """
                .trimIndent()

        JsonLenient.decodeFromString<OllamaChatResponse>(response).apply {
            assertEquals("llama2", model)
            assertEquals("2023-08-04T08:52:19.385406455-07:00", createdAt)
            assertEquals("assistant", message?.role)
            assertEquals("The", message?.content)
            assertNull(message?.images)
            assertFalse(done)
        }
    }
}
