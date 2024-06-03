package com.tddworks.openai.api.images.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ResponseFormatTest {
    @Test
    fun `should return correct base 64 format type`() {
        assertEquals("b64_json", ResponseFormat.base64.value)
    }

    @Test
    fun `should return correct url format type`() {
        assertEquals("url", ResponseFormat.url.value)
    }
}