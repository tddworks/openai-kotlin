package com.tddworks.anthropic.api

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AnthropicTest {

    val anthropic: Anthropic = Anthropic.create(AnthropicConfig())

    @Test
    fun `should return default settings`() {
        assertEquals("api.anthropic.com", anthropic.baseUrl())

        assertEquals("CONFIG_API_KEY", anthropic.apiKey())

        assertEquals("api.anthropic.com", anthropic.baseUrl())

        assertEquals("2023-06-01", anthropic.anthropicVersion())
    }

}