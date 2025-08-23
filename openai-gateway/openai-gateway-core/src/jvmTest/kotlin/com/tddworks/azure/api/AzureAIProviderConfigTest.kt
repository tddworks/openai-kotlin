package com.tddworks.azure.api

import com.tddworks.openai.gateway.api.OpenAIProviderConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AzureAIProviderConfigTest {

    @Test
    fun `should create AzureAIProviderConfig`() {
        val config =
            OpenAIProviderConfig.azure(
                apiKey = { "api-key" },
                baseUrl = { "YOUR_RESOURCE_NAME.openai.azure.com" },
                deploymentId = { "deployment-id" },
                apiVersion = { "api-version" },
            )

        assertEquals("api-key", config.apiKey())
        assertEquals("YOUR_RESOURCE_NAME.openai.azure.com", config.baseUrl())
        assertEquals("deployment-id", config.deploymentId())
        assertEquals("api-version", config.apiVersion())
    }
}
