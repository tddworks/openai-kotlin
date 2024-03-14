package com.tddworks.anthropic.api


import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*

/**
 * See https://ktor.io/docs/http-client-testing.html#usage
 */
fun mockHttpClient(mockResponse: String) = HttpClient(MockEngine) {

    val headers = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))

    install(ContentNegotiation) {
        register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
    }

    engine {
        addHandler { request ->
            if (request.url.encodedPath == "/v1/messages") {
                respond(mockResponse, HttpStatusCode.OK, headers)
            } else {
                error("Unhandled ${request.url.encodedPath}")
            }
        }
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "api.lemonsqueezy.com"
        }

        header(HttpHeaders.ContentType, ContentType.Application.Json)
        contentType(ContentType.Application.Json)
    }
}