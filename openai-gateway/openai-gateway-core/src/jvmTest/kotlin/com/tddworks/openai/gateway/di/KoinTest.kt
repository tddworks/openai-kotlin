package com.tddworks.openai.gateway.di


import com.tddworks.anthropic.api.AnthropicConfig
import com.tddworks.ollama.api.OllamaConfig
import com.tddworks.openai.api.OpenAIConfig
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.mockito.Mockito.mock

class OpenAIGatewayKoinTest : KoinTest {

    @Test
    fun `should initialize OpenAI Gateway Koin modules`() {
        val openAIConfig = mock<OpenAIConfig>()
        val anthropicConfig = mock<AnthropicConfig>()
        val ollamaConfig = mock<OllamaConfig>()

        koinApplication {
            initOpenAIGatewayKoin(openAIConfig, anthropicConfig, ollamaConfig)
        }.checkModules()
    }
}

