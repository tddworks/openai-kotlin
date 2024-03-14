package com.tddworks.common.network.api.ktor.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import io.ktor.client.engine.darwin.*


actual fun HttpRequester.Companion.default(
    url: String,
    token: String,
): HttpRequester {
    return DefaultHttpRequester(
        createHttpClient(
            url = url,
            token = token,
            engine = Darwin
        )
    )
}