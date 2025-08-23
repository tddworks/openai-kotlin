package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

internal actual fun httpClientEngine(): HttpClientEngine {
    return OkHttp.create()
}
