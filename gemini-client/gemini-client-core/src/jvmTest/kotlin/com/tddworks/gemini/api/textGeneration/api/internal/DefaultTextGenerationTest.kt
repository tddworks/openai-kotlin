package com.tddworks.gemini.api.textGeneration.api.internal

import app.cash.turbine.test
import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.common.network.api.ktor.internal.JsonLenient
import com.tddworks.gemini.api.textGeneration.api.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.junit5.KoinTestExtension

class DefaultTextGenerationTest : KoinTest {
    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(
            module {
                single<Json> { JsonLenient }
            })
    }

    @Test
    fun `should return correct streamGenerateContent response`() = runTest {
        // Given
        val jsonResponse =
            """data: {"candidates": [{"content": {"parts": [{"text": "some-text"}],"role": "model"},"finishReason": "STOP"}],"usageMetadata": {"promptTokenCount": 4,"candidatesTokenCount": 724,"totalTokenCount": 728},"modelVersion": "gemini-1.5-flash"}"""
        val textGenerationApi = DefaultTextGenerationApi(
            requester = DefaultHttpRequester(
                httpClient = mockHttpClient(jsonResponse)
            )
        )
        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part.TextPart("some-text")), role = "model")),
            stream = true
        )

        // When
        textGenerationApi.streamGenerateContent(request).test {
            // Then
            assertEquals(
                GenerateContentResponse(
                    candidates = listOf(
                        Candidate(
                            content = Content(
                                parts = listOf(Part.TextPart("some-text")),
                                role = "model"
                            ),
                            finishReason = "STOP"
                        )
                    ),
                    usageMetadata = UsageMetadata(
                        promptTokenCount = 4,
                        candidatesTokenCount = 724,
                        totalTokenCount = 728
                    ),
                    modelVersion = "gemini-1.5-flash"
                ), awaitItem()
            )
            awaitComplete()
        }
    }

    @Test
    fun `should return correct generateContent response`() = runTest {
        // Given
        val jsonResponse = """
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
        """.trimIndent()
        val textGenerationApi = DefaultTextGenerationApi(
            requester = DefaultHttpRequester(
                httpClient = mockHttpClient(jsonResponse)
            )
        )
        val request = GenerateContentRequest(
            contents = listOf(Content(parts = listOf(Part.TextPart("some-text")), role = "model")),
        )

        // When
        val response = textGenerationApi.generateContent(request)

        // Then
        assertEquals("STOP", response.candidates[0].finishReason)
        assertEquals(-0.24741496906413898, response.candidates[0].avgLogprobs)
        assertEquals("model", response.candidates[0].content.role)
        assertEquals(1, response.candidates[0].content.parts.size)
        assertEquals("some-text", (response.candidates[0].content.parts[0] as Part.TextPart).text)
        assertEquals("gemini-1.5-flash", response.modelVersion)
        assertEquals(4, response.usageMetadata.promptTokenCount)
        assertEquals(715, response.usageMetadata.candidatesTokenCount)
        assertEquals(719, response.usageMetadata.totalTokenCount)
    }

}