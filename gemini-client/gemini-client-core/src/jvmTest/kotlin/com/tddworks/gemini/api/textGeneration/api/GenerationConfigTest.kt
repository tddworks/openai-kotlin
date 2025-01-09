package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class GenerationConfigTest {

    @Test
    fun `should return correct generation config`() {
        // Given
        val generationConfig = GenerationConfig(
            temperature = 1.0f,
            responseMimeType = "application/json"
        )

        // When
        val result = Json.encodeToString(
            GenerationConfig.serializer(),
            generationConfig
        )

        // Then
        JSONAssert.assertEquals(
            """
            {
                "temperature": 1.0,
                "response_mime_type": "application/json"
            }
            """.trimIndent(),
            result,
            false
        )
    }
}