package com.tddworks.gemini.api.textGeneration.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GenerateContentRequestTest {

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