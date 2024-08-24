package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.call.*
import io.ktor.client.engine.mock.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HttpClientTest {

    private val mockEngine = MockEngine { request ->
        when (request.url.toString()) {
            "https://some-host" -> respond(
                content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )

            else -> respond("", HttpStatusCode.NotFound)
        }
    }

    @Test
    fun `should return correct response with host and port based config with query parameters`() =
        runTest {
            val apiClient = createHttpClient(
                connectionConfig = HostPortConnectionConfig(
                    protocol = { "https" },
                    port = { 443 },
                    host = { "some-host" }
                ),
                httpClientEngine = mockEngine,
                authConfig = AuthConfig(authToken = { "token" }),
                features = ClientFeatures(
                    queryParams = mapOf("key" to "value"),
                )
            )

            val body = apiClient.get("https://some-host").body<String>()
            assertEquals("""{"ip":"127.0.0.1"}""", body)
        }

    @Test
    fun `should return correct response with host and port based config`() = runTest {
        val apiClient = createHttpClient(
            connectionConfig = HostPortConnectionConfig(
                protocol = { "https" },
                port = { 443 },
                host = { "some-host" }
            ),
            httpClientEngine = mockEngine
        )

        val body = apiClient.get("https://some-host").body<String>()
        assertEquals("""{"ip":"127.0.0.1"}""", body)
    }

    @Test
    fun `should return correct response with url based config`() = runTest {
        val apiClient = createHttpClient(
            connectionConfig = UrlBasedConnectionConfig { "https://some-host" },
            httpClientEngine = mockEngine
        )

        val body = apiClient.get("https://some-host").body<String>()
        assertEquals("""{"ip":"127.0.0.1"}""", body)
    }

    @Test
    fun `should return correct response with default`() = runTest {
        val apiClient = createHttpClient(httpClientEngine = mockEngine)

        val body = apiClient.get("https://some-host").body<String>()
        assertEquals("""{"ip":"127.0.0.1"}""", body)
    }

    @Test
    fun `should return OkHttp engine`() {
        val httpClientEngine = httpClientEngine()
        assertTrue(httpClientEngine is OkHttpEngine)
    }

}