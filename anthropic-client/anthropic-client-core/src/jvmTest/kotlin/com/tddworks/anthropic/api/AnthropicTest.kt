package com.tddworks.anthropic.api

import com.tddworks.anthropic.di.iniAnthropic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class AnthropicTest {

    val anthropic: Anthropic = Anthropic.create(AnthropicConfig())

    @Test
    fun `should return default settings`() {
        assertEquals("api.anthropic.com", anthropic.baseUrl())

        assertEquals("CONFIG_API_KEY", anthropic.apiKey())

        assertEquals("CONFIG_API_KEY", anthropic.apiKey())

        assertEquals("2023-06-01", anthropic.anthropicVersion())
    }

}