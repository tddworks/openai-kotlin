package com.tddworks.anthropic.api

import com.tddworks.anthropic.di.iniAnthropic
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.test.junit5.AutoCloseKoinTest

class AnthropicTest : AutoCloseKoinTest() {

    lateinit var anthropic: Anthropic

    @BeforeEach
    fun setUp() {
        anthropic = iniAnthropic(
            config = AnthropicConfig()
        )
    }

    @Test
    fun `should return overridden settings`() {
        val target = Anthropic(
            baseUrl = { "http://localhost:8080" },
            apiKey = { "1234" },
            anthropicVersion = { "2024-03-01" },
        )

        assertEquals("http://localhost:8080", target.baseUrl())

        assertEquals("1234", target.apiKey())

        assertEquals("2024-03-01", target.anthropicVersion())
    }

    @Test
    fun `should return default settings`() {
        assertEquals("api.anthropic.com", anthropic.baseUrl())

        assertEquals("CONFIG_API_KEY", anthropic.apiKey())

        assertEquals("CONFIG_API_KEY", anthropic.apiKey())

        assertEquals("2023-06-01", anthropic.anthropicVersion())
    }

}