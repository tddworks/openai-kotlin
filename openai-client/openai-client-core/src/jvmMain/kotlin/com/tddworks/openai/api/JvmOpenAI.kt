package com.tddworks.openai.api

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.internal.default

fun JvmOpenAI(token: String): OpenAI = OpenAIApi(
    HttpRequester.default(
        url = OpenAI.BASE_URL,
        token = token,
    )
)