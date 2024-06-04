package com.tddworks.openai.di

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIConfig
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest

class OpenAIKoinTest : AutoCloseKoinTest() {

    @Test
    fun `should initialize openai koin modules`() {
        koinApplication {
            initOpenAI(OpenAIConfig(
                baseUrl = { OpenAI.BASE_URL },
                apiKey = { System.getenv("OPENAI_API_KEY") ?: "CONFIGURE_ME" }
            ))
        }.checkModules()
    }
}