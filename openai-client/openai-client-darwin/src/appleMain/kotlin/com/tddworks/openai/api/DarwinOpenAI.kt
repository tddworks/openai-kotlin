package com.tddworks.openai.api

import com.tddworks.openai.api.internal.network.ktor.HttpRequester
import com.tddworks.openai.api.internal.network.ktor.default
import io.ktor.client.engine.darwin.*

fun DarwinOpenAI(token: String): OpenAI = OpenAIApi(
    HttpRequester.default(
        url = OpenAI.BASE_URL,
        token = token,
        engine = Darwin.create()
    )
)

fun DarwinOpenAI(token: String, url: String): OpenAI = OpenAIApi(
    HttpRequester.default(
        url = url,
        token = token,
        engine = Darwin.create()
    )
)
