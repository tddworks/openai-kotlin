package com.tddworks.openai.api.internal.network.ktor.internal

import com.tddworks.openai.api.JsonLenient
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlin.time.Duration.Companion.minutes

/**
 * Creates a new [HttpClient] with [OkHttp] engine and [ContentNegotiation] plugin.
 *
 * @param url the base URL of the API
 * @param token the authentication token
 * @return a new [HttpClient] instance
 */
internal fun <T : HttpClientEngineConfig> createHttpClient(
    url: String,
    token: String,
    engine: HttpClientEngineFactory<T>,
): HttpClient {
    return HttpClient(engine) {
//      enable proxy in the future
//      engine {
//          proxy = ProxyBuilder.http(url)
//      }

        install(ContentNegotiation) {
            register(ContentType.Application.Json, KotlinxSerializationConverter(JsonLenient))
        }

        /**
         * Support configurable in the future
         * Install the Logging module.
         * @param logging the logging instance to use
         * @return Unit
         */
        install(Logging) {
            /**
             * DEFAULT - default - LoggerFactory.getLogger
             * SIMPLE - Logger using println.
             * Empty - Empty Logger for test purpose.
             */
            logger = Logger.SIMPLE
            /**
             * ALL - log all
             * HEADERS - log headers
             * INFO - log info
             * NONE - none
             */
            level = LogLevel.ALL
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(accessToken = token, refreshToken = "")
                }
            }
        }


        /**
         * Installs an [HttpRequestRetry] with default maxRetries of 3,
         * retryIf checks for rate limit error with status code 429,
         * and exponential delay with base 5.0 and max delay of 1 minute.
         *
         * @param retry [HttpRequestRetry] instance to install
         */
        install(HttpRequestRetry) {
            maxRetries = 3
            // retry on rate limit error.
            retryIf { _, response -> response.status.value.let { it == 429 } }
            exponentialDelay(base = 5.0, maxDelayMs = 1.minutes.inWholeMilliseconds)
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = url
            }

            header(HttpHeaders.ContentType, ContentType.Application.Json)
            contentType(ContentType.Application.Json)
        }

        /**
         * If set to true, the client will throw an exception if the response from the server is not successful. The definition of successful can vary depending on the HTTP status code. For example, a successful response for a GET request would typically be a status code of 200, while a successful response for a POST request could be a status code of 201.
         *
         * By setting expectSuccess = true, the developer is indicating that they want to handle non-successful responses explicitly and can throw or handle the exceptions themselves.
         *
         * If expectSuccess is set to false, the HttpClient will not throw exceptions for non-successful responses and the developer is responsible for parsing and handling any errors or unexpected responses.
         */
        expectSuccess = true

    }
}

