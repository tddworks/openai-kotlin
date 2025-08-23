package com.tddworks.gemini.api

import com.tddworks.gemini.api.textGeneration.api.Gemini
import com.tddworks.gemini.api.textGeneration.api.GeminiConfig
import com.tddworks.gemini.di.initGemini

/**
 * A singleton object that initializes the Gemini configuration with the specified API key and base
 * URL.
 */
object DarwinGemini {

    /**
     * Initializes the Gemini configuration with the specified API key and base URL.
     *
     * This function sets up the Gemini environment by creating a configuration using the provided
     * API key and base URL, then initializing Gemini with this configuration.
     *
     * @param apiKey A lambda function that returns the API key as a string. Defaults to returning
     *   "CONFIG_API_KEY".
     * @param baseUrl A lambda function that returns the base URL as a string. Defaults to returning
     *   `Gemini.BASE_URL`.
     * @return The initialized Gemini configuration.
     */
    fun gemini(
        apiKey: () -> String = { "CONFIG_API_KEY" },
        baseUrl: () -> String = { Gemini.BASE_URL },
    ): Gemini = initGemini(GeminiConfig(apiKey, baseUrl)).koin.get<Gemini>()
}
