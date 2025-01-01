package com.tddworks.gemini.api

import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.gemini.api.textGeneration.api.GeminiConfig
import com.tddworks.gemini.di.initGemini

/**
 * Object responsible for setting up and initializing the Anthropoc API client.
 */
object DarwinGemini {
//
    /**
     * Initializes the Anthropic library with the provided configuration parameters.
     *
     * @param apiKey a lambda function that returns the API key to be used for authentication
     * @param baseUrl a lambda function that returns the base URL of the Anthropic API
     * @param anthropicVersion a lambda function that returns the version of the Anthropic API to use
     */
    fun anthropic(
        apiKey: () -> String = { "CONFIG_API_KEY" },
        baseUrl: () -> String = { Gemini.BASE_URL },
    ) = initGemini(GeminiConfig(apiKey, baseUrl))
}
