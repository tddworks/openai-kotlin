package com.tddworks.openai.api

import com.tddworks.openai.api.internal.network.ktor.HttpRequester
import com.tddworks.openai.api.internal.network.ktor.default
import io.ktor.client.engine.darwin.*

/**
 * Create an instance of OpenAI with the Darwin engine.
 * @param token The API token to use for authentication.
 * @return An instance of OpenAI.
 */
fun OpenAIApi.create(token: String): OpenAI = OpenAIApi(
    HttpRequester.default(
        url = OpenAI.BASE_URL,
        token = token,
        engine = Darwin.create()
    )
)

/**
 * Create an instance of OpenAI with the Darwin engine.
 * @param token A function that returns the API token to use for authentication.
 * @param url A function that returns the base URL of the API.
 * @return An instance of OpenAI.
 */
fun OpenAIApi.create(token: String, url: String): OpenAIApi {
    return OpenAIApi(
        HttpRequester.default(
            url = url,
            token = token,
            engine = Darwin.create()
        )
    )
}


/**
 * Create an instance of OpenAI with the Darwin engine.
 * @param token A function that returns the API token to use for authentication.
 * @param url A function that returns the base URL of the API.
 * @return An instance of OpenAI.
 */
fun OpenAIApi.create(token: () -> String, url: () -> String): OpenAI = OpenAIApi(
    HttpRequester.default(
        url = url(),
        token = token(),
        engine = Darwin.create()
    )
)
