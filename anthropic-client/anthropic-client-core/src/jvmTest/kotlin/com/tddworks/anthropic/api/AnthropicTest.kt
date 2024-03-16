package com.tddworks.anthropic.api

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AnthropicTest {

    @Test
    fun `should return overridden settings`() {
        val target = Anthropic(
            baseUrl = "http://localhost:8080",
            apiKey = "1234"
        )

        assertEquals("http://localhost:8080", target.baseUrl())

        assertEquals("1234", target.apiKey())

    }

    @Test
    fun `should return default settings`() {
        val target = Anthropic()

        assertEquals("api.anthropic.com", target.baseUrl())

        assertEquals("CONFIGURE_ME", target.apiKey())

    }

}