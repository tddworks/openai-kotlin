package com.tddworks.gemini.api.textGeneration.api

import kotlin.test.assertEquals
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test

class GenerateContentResponseTest {

    @Test
    fun `should deserialize GenerateContentResponse`() {
        // Given
        val json =
            """
            {
              "candidates": [
                {
                  "content": {
                    "parts": [
                      {
                        "text": "some-text"
                      }
                    ],
                    "role": "model"
                  },
                  "finishReason": "STOP",
                  "avgLogprobs": -0.24741496906413898
                }
              ],
              "usageMetadata": {
                "promptTokenCount": 4,
                "candidatesTokenCount": 715,
                "totalTokenCount": 719
              },
              "modelVersion": "gemini-1.5-flash"
            }
        """
                .trimIndent()

        // When
        val response = Json.decodeFromString<GenerateContentResponse>(json)

        // Then
        assertEquals(1, response.candidates.size)
        assertEquals("gemini-1.5-flash", response.modelVersion)
        assertEquals(4, response.usageMetadata.promptTokenCount)
        assertEquals(715, response.usageMetadata.candidatesTokenCount)
        assertEquals(719, response.usageMetadata.totalTokenCount)
        assertEquals("STOP", response.candidates[0].finishReason)
        assertEquals(-0.24741496906413898, response.candidates[0].avgLogprobs)
        assertEquals("model", response.candidates[0].content.role)
        assertEquals(1, response.candidates[0].content.parts.size)
        assertEquals("some-text", (response.candidates[0].content.parts[0] as Part.TextPart).text)
    }
}
