package com.tddworks.common.network.api


import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.utils.io.*

/**
 * See https://ktor.io/docs/http-client-testing.html#usage
 */
fun mockHttpClient(
    mockResponse: String,
    mockHttpStatusCode: HttpStatusCode = HttpStatusCode.OK,
    exception: Exception? = null
) = HttpClient(MockEngine) {

    val headers =
        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    install(ContentNegotiation) {
        register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
    }

    engine {
        addHandler { request ->

            exception?.let {
                throw it
            }

            if (mockHttpStatusCode == HttpStatusCode.OK || mockHttpStatusCode == HttpStatusCode.Forbidden) {
                if (request.url.encodedPath == "/v1/chat/completions"
                    || request.url.encodedPath == "/v1/images/generations"
                ) {
                    respond(mockResponse, mockHttpStatusCode, headers)
                } else {
                    error("Unhandled ${request.url.encodedPath}")
                }
            } else {
                respondError(mockHttpStatusCode)
            }
        }
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "some-host"
        }

        header(HttpHeaders.ContentType, ContentType.Application.Json)
        contentType(ContentType.Application.Json)
    }
}