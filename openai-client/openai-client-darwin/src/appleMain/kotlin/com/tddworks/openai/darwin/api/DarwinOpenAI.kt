package com.tddworks.openai.darwin.api

import com.tddworks.openai.api.OpenAI
import com.tddworks.openai.api.OpenAIApi

object DarwinOpenAI {
    /**
     * Create an instance of OpenAI with the Darwin engine.
     * @param token The API token to use for authentication.
     * @return An instance of OpenAI.
     */
    fun create(token: String): OpenAI = OpenAIApi()

    /**
     * Create an instance of OpenAI with the Darwin engine.
     * @param token A function that returns the API token to use for authentication.
     * @param url A function that returns the base URL of the API.
     * @return An instance of OpenAI.
     */
    fun create(token: String, url: String) = OpenAIApi()


    /**
     * Create an instance of OpenAI with the Darwin engine.
     * @param token A function that returns the API token to use for authentication.
     * @param url A function that returns the base URL of the API.
     * @return An instance of OpenAI.
     */
    fun create(token: () -> String, url: () -> String): OpenAI = OpenAIApi()
}
