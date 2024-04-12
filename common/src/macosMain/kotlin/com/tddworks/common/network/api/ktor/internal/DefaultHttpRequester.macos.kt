package com.tddworks.common.network.api.ktor.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester


actual fun HttpRequester.Companion.default(
    url: String,
    token: String,
): HttpRequester {
    return DefaultHttpRequester(
        createHttpClient(
            url = { url },
            authToken = { token },
            json = JsonLenient
        )
    )
}