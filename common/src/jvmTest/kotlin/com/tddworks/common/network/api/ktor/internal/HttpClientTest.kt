package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.call.*
import io.ktor.client.engine.mock.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HttpClientTest {

    @Test
    fun `should return correct json response with default settings`() {
        runBlocking {
            val mockEngine = MockEngine { request ->
                respond(
                    content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
            val apiClient = createHttpClient(
                host = { "some-host" },
                httpClientEngine = mockEngine
            )

            val body = apiClient.get("https://some-host:443").body<String>()
            assertEquals("""{"ip":"127.0.0.1"}""", body)
        }
    }


    @Test
    fun `should return OkHttp engine`() {
        val httpClientEngine = httpClientEngine()
        assertTrue(httpClientEngine is OkHttpEngine)
    }

}