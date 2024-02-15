package com.tddworks.openai.api.internal.network.ktor

import com.tddworks.openai.api.internal.network.ktor.internal.createHttpClient
import io.ktor.client.engine.cio.*

actual fun HttpRequester.Companion.default(
    url: String,
    token: String,
): HttpRequester {
    return DefaultHttpRequester(
        createHttpClient(
            url = url,
            token = token,
            engine = CIO
        )
    )
}