package com.tddworks.gemini.api.textGeneration.api

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert

class GenerateContentRequestTest {

    @Test
    fun `should return correct text and image request`() {

        // Given
        val generateContentRequest = GenerateContentRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part.TextPart(text = "hello"),
                        Part.InlineDataPart(
                            inlineData = Part.InlineDataPart.InlineData(
                                "image/jpeg",
                                "base64"
                            )
                        )
                    )
                )
            ),
            stream = false
        )

        // When
        val result = Json.encodeToString(
            GenerateContentRequest.serializer(),
            generateContentRequest
        )

        // Then
        JSONAssert.assertEquals(
            """
                {
                  "contents": [{
                    "parts":[
                      {"text": "hello"},
                      {
                        "inline_data": {
                          "mime_type":"image/jpeg",
                          "data": "base64"
                        }
                      }
                    ]
                  }]
                }
            """.trimIndent(),
            result,
            false
        )
    }

    @Test
    fun `should return correct streamGenerateContent request url`() {
        // Given
        val generateContentRequest = GenerateContentRequest(
            contents = listOf(),
            stream = true,
        )

        // When
        val result = generateContentRequest.toRequestUrl()

        // Then
        assertEquals(
            "/v1beta/models/gemini-1.5-flash:streamGenerateContent",
            result
        )
    }


    @Test
    fun `should return correct generateContent request url`() {
        // Given
        val generateContentRequest = GenerateContentRequest(
            contents = listOf(),
            stream = false
        )

        // When
        val result = generateContentRequest.toRequestUrl()

        // Then
        assertEquals(
            "/v1beta/models/gemini-1.5-flash:generateContent",
            result
        )
    }

}