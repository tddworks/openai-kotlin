package com.tddworks.openai.di

import com.tddworks.di.getInstance
import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIConfig
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.koin.dsl.koinApplication
import org.koin.test.check.checkModules
import org.koin.test.junit5.AutoCloseKoinTest

class OpenAIKoinTest : AutoCloseKoinTest() {
    @Test
    fun `should initialize common koin modules`() {
        koinApplication {
            initOpenAI(OpenAIConfig(
                baseUrl = { OpenAI.BASE_URL },
                apiKey = { System.getenv("OPENAI_API_KEY") ?: "CONFIGURE_ME" }
            ))
        }.checkModules()

        val json = getInstance<Json>()

        kotlin.test.assertNotNull(json)
    }
}

