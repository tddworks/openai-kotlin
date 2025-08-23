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
        is HostPortConnectionConfig -> setupHostPortConnectionConfig(connectionConfig)
        is UrlBasedConnectionConfig -> url.takeFrom(connectionConfig.baseUrl())
    }
}

private fun DefaultRequest.DefaultRequestBuilder.setupHostPortConnectionConfig(
    config: HostPortConnectionConfig
) {
    url {
        protocol = config.protocol()?.let { URLProtocol.createOrDefault(it) } ?: URLProtocol.HTTPS
        host = config.host()
        config.port()?.let { port = it }
    }
}
