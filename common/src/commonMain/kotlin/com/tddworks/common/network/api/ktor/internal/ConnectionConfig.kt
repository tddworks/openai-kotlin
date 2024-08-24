package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.plugins.*
import io.ktor.http.*

interface ConnectionConfig {
    fun setupUrl(builder: DefaultRequest.DefaultRequestBuilder) {
        builder.setupUrl(this)
    }
}

private fun DefaultRequest.DefaultRequestBuilder.setupUrl(connectionConfig: ConnectionConfig) {
    when (connectionConfig) {
        is HostPortConnectionConfig -> {
            url {
                protocol =
                    connectionConfig.protocol()?.let { URLProtocol.createOrDefault(it) }
                        ?: URLProtocol.HTTPS
                host = connectionConfig.host()
                connectionConfig.port()?.let { port = it }
            }
        }

        is UrlBasedConnectionConfig -> {
            connectionConfig.baseUrl().let { url.takeFrom(it) }
        }
    }
}