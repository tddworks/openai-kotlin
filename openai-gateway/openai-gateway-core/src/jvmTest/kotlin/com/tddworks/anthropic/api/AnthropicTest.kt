package com.tddworks.anthropic.api

import com.tddworks.anthropic.di.iniAnthropic
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class AnthropicTest : AutoCloseKoinTest() {

    @BeforeEach
    fun setUp() {
        iniAnthropic(config = AnthropicConfig())
    }

    @Test
    fun `should create anthropic with default settings`() {

        val anthropic = Anthropic.create(anthropicConfig = AnthropicConfig())

        assertEquals("CONFIG_API_KEY", anthropic.apiKey())
        assertEquals(Anthropic.BASE_URL, anthropic.baseUrl())
        assertEquals("2023-06-01", anthropic.anthropicVersion())
    }
}
