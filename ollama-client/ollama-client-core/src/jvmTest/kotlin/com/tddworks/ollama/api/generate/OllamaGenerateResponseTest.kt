package com.tddworks.ollama.api.generate

import com.tddworks.ollama.api.json.JsonLenient
import com.tddworks.ollama.api.prettyJson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OllamaGenerateResponseTest {

    @Test
    fun `should convert to generate response from stream json with done`() {
        // Given
        val json = """
            {
                "model": "deepseek-coder:6.7b",
                "created_at": "2024-06-19T04:21:28.204638Z",
                "response": "",
                "done": true,
                "done_reason": "length",
                "total_duration": 7864500542,
                "load_duration": 5949281959,
                "prompt_eval_count": 181,
                "prompt_eval_duration": 308480000,
                "eval_count": 100,
                "eval_duration": 1603405000
            }
        """.trimIndent()

        // When
        val response =
            JsonLenient.decodeFromString(OllamaGenerateResponse.serializer(), json)

        // Then
        assertEquals("deepseek-coder:6.7b", response.model)
        assertEquals("2024-06-19T04:21:28.204638Z", response.createdAt)
        assertEquals("", response.response)
        assertEquals(true, response.done)
        assertEquals("length", response.doneReason)
        assertEquals(7864500542, response.totalDuration)
        assertEquals(5949281959, response.loadDuration)
        assertEquals(181, response.promptEvalCount)
        assertEquals(308480000, response.promptEvalDuration)
        assertEquals(100, response.evalCount)
        assertEquals(1603405000, response.evalDuration)
    }

    @Test
    fun `should convert to generate response from stream json with not done`() {
        // Given
        val json = """
            {
                "model": "deepseek-coder:6.7b",
                "created_at": "2024-06-19T04:21:27.735135Z",
                "response": "Media",
                "done": false
            }
        """.trimIndent()

        // When
        val response =
            JsonLenient.decodeFromString(OllamaGenerateResponse.serializer(), json)

        // Then
        assertEquals("deepseek-coder:6.7b", response.model)
        assertEquals("2024-06-19T04:21:27.735135Z", response.createdAt)
        assertEquals("Media", response.response)
        assertEquals(false, response.done)
    }

    @Test
    fun `should convert to json from OllamaGenerateResponse`() {
        // Given
        val response = OllamaGenerateResponse(
            model = "llama3",
            createdAt = "2023-08-04T19:22:45.499127Z",
            response = "",
            done = true,
            context = listOf(1, 2, 3),
            totalDuration = 10706818083,
            loadDuration = 6338219291,
            promptEvalCount = 26,
            promptEvalDuration = 130079000,
            evalCount = 259,
            evalDuration = 4232710000
        )

        // When
        val json =
            prettyJson.encodeToString(OllamaGenerateResponse.serializer(), response)

        // Then
        assertEquals(
            """
            {
              "model": "llama3",
              "created_at": "2023-08-04T19:22:45.499127Z",
              "response": "",
              "done": true,
              "context": [
                1,
                2,
                3
              ],
              "total_duration": 10706818083,
              "load_duration": 6338219291,
              "prompt_eval_count": 26,
              "prompt_eval_duration": 130079000,
              "eval_count": 259,
              "eval_duration": 4232710000
            }
            """.trimIndent(),
            json
        )
    }

    @Test
    fun `should convert json to OllamaGenerateResponse`() {
        // Given
        val json = """
            {
              "model": "llama3",
              "created_at": "2023-08-04T19:22:45.499127Z",
              "response": "",
              "done": true,
              "context": [1, 2, 3],
              "total_duration": 10706818083,
              "load_duration": 6338219291,
              "prompt_eval_count": 26,
              "prompt_eval_duration": 130079000,
              "eval_count": 259,
              "eval_duration": 4232710000
            }
        """.trimIndent()

        // When
        val response =
            JsonLenient.decodeFromString(OllamaGenerateResponse.serializer(), json)

        // Then
        assertEquals("llama3", response.model)
        assertEquals("2023-08-04T19:22:45.499127Z", response.createdAt)
        assertEquals("", response.response)
        assertEquals(true, response.done)
        assertEquals(listOf(1, 2, 3), response.context)
        assertEquals(10706818083, response.totalDuration)
        assertEquals(6338219291, response.loadDuration)
        assertEquals(26, response.promptEvalCount)
        assertEquals(130079000, response.promptEvalDuration)
        assertEquals(259, response.evalCount)
        assertEquals(4232710000, response.evalDuration)
    }
}