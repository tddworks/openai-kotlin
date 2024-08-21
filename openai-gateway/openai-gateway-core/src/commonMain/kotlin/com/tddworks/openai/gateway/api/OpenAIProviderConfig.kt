package com.tddworks.openai.gateway.api

/**
 * Represents the configuration for the OpenAI API.
 */
interface OpenAIProviderConfig {
    val apiKey: () -> String
    val baseUrl: () -> String
    companion object
}