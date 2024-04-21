package com.tddworks.openai.darwin.api

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIConfig
import com.tddworks.openai.di.initOpenAI

/**
 * Object for accessing OpenAI API functions
 */
object DarwinOpenAI {

    /**
     * Function to initialize the OpenAI API client with the given API key and base URL.
     *
     * @param apiKey A lambda function that returns the API key to be used for authentication. Defaults to "CONFIG_API_KEY".
     * @param baseUrl A lambda function that returns the base URL of the OpenAI API. Defaults to the constant OpenAI.BASE_URL.
     */
    fun openAI(
        apiKey: () -> String = { "CONFIG_API_KEY" },
        baseUrl: () -> String = { OpenAI.BASE_URL },
    ) = initOpenAI(OpenAIConfig(apiKey, baseUrl))
}
