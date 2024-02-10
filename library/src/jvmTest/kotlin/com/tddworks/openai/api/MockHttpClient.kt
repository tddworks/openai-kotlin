package com.tddworks.openai.api

import com.tddworks.openai.api.chat.capabilities.vision.VisionMessageContent
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

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
            if (request.url.encodedPath == "/v1/chat/completions") {
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

/**
 * Represents a JSON object that allows for leniency and ignores unknown keys.
 *
 * @property isLenient Removes JSON specification restriction (RFC-4627) and makes parser more liberal to the malformed input. In lenient mode quoted boolean literals, and unquoted string literals are allowed.
 * Its relaxations can be expanded in the future, so that lenient parser becomes even more permissive to invalid value in the input, replacing them with defaults.
 * false by default.
 * @property ignoreUnknownKeys Specifies whether encounters of unknown properties in the input JSON should be ignored instead of throwing SerializationException. false by default..
 */
internal val JsonLenient = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
}