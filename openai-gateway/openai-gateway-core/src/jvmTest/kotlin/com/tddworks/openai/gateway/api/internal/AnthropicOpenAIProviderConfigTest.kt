package com.tddworks.openai.gateway.api.internal

import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AnthropicOpenAIProviderConfigTest {

    @Test
    fun `should create anthropic openai config`() {
        // When
        val r =
            OpenAIProviderConfig.anthropic(
                apiKey = { "apiKey" },
                baseUrl = { "baseUrl" },
                anthropicVersion = { "2023-06-01" },
            )

        // Then
        assertEquals("apiKey", r.apiKey())
        assertEquals("baseUrl", r.baseUrl())
        assertEquals("2023-06-01", r.anthropicVersion())
    }

    @Test
    fun `should convert to anthropic openai config`() {
        // Given
        val anthropicOpenAIProviderConfig =
            AnthropicOpenAIProviderConfig(
                anthropicVersion = { "2023-06-01" },
                apiKey = { "apiKey" },
                baseUrl = { "baseUrl" },
            )

        // When
        val anthropicConfig = anthropicOpenAIProviderConfig.toAnthropicOpenAIConfig()

        // Then
        assertEquals("apiKey", anthropicConfig.apiKey())
        assertEquals("baseUrl", anthropicConfig.baseUrl())
        assertEquals("2023-06-01", anthropicConfig.anthropicVersion())
    }
}
