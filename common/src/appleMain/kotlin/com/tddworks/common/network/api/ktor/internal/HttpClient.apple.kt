package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

internal actual fun httpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig> {
    return Darwin
}