package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.util.*
import kotlinx.serialization.json.Json


internal expect fun httpClientEngine(): HttpClientEngine


interface ConnectionConfig {
    fun setupUrl(builder: DefaultRequest.DefaultRequestBuilder) {
        builder.setupUrl(this)
    }
}

data class UrlBasedConnectionConfig(
    val baseUrl: () -> String = { "" }
) : ConnectionConfig

data class HostPortConnectionConfig(
    val protocol: () -> String? = { null },
    val host: () -> String = { "" },
    val port: () -> Int? = { null },
) : ConnectionConfig

data class AuthConfig(
    val authToken: (() -> String)? = null
)

data class ClientFeatures(
    val json: Json = Json,
    val queryParams: Map<String, String> = emptyMap(),
    val expectSuccess: Boolean = true
)

fun createHttpClient(
    connectionConfig: ConnectionConfig = UrlBasedConnectionConfig(),
    authConfig: AuthConfig = AuthConfig(),
    features: ClientFeatures = ClientFeatures(),
    httpClientEngine: HttpClientEngine = httpClientEngine()
): HttpClient {

    return HttpClient(httpClientEngine) {
        install(ContentNegotiation) {
            register(
                ContentType.Application.Json,
                KotlinxSerializationConverter(features.json)
            )
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> response.status.value == 429 }
            exponentialDelay(base = 5.0, maxDelayMs = 60_000)
        }


        defaultRequest {
            connectionConfig.setupUrl(this)
            commonSettings(features.queryParams, authConfig.authToken)
        }

        expectSuccess = features.expectSuccess
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

private fun DefaultRequest.DefaultRequestBuilder.commonSettings(
    queryParams: Map<String, String>,
    authToken: (() -> String)?
) {
    queryParams.forEach { (key, value) ->
        url.parameters.appendIfNameAbsent(
            key,
            value
        )
    }

    authToken?.let {
        header(HttpHeaders.Authorization, "Bearer ${it()}")
    }

    header(HttpHeaders.ContentType, ContentType.Application.Json)
    contentType(ContentType.Application.Json)
}

