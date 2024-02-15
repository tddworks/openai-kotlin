package com.tddworks.openai.api

import com.tddworks.openai.api.internal.network.ktor.HttpRequester
import com.tddworks.openai.api.internal.network.ktor.default
import io.ktor.client.engine.cio.*

fun JvmOpenAI(token: String): OpenAI = OpenAIApi(
    HttpRequester.default(
        url = OpenAI.BASE_URL,
        token = token,
    )
)