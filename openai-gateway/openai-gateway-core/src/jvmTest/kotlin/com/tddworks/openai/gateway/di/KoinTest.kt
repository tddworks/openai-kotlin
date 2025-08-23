package com.tddworks.openai.gateway.di

import com.tddworks.openai.gateway.api.internal.AnthropicOpenAIProviderConfig
import com.tddworks.openai.gateway.api.internal.DefaultOpenAIProviderConfig
import com.tddworks.openai.gateway.api.internal.GeminiOpenAIProviderConfig
import com.tddworks.openai.gateway.api.internal.OllamaOpenAIProviderConfig
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class OpenAIGatewayKoinTest : KoinTest {

    @Test
    fun `should initialize OpenAI Gateway Koin modules`() {
        val openAIConfig = DefaultOpenAIProviderConfig(apiKey = { "" })
        val anthropicConfig = AnthropicOpenAIProviderConfig(apiKey = { "" })
        val ollamaConfig = OllamaOpenAIProviderConfig()
        val geminiConfig = GeminiOpenAIProviderConfig(apiKey = { "" })

        koinApplication {
                initOpenAIGateway(openAIConfig, anthropicConfig, ollamaConfig, geminiConfig)
            }
            .checkModules()
    }
}
