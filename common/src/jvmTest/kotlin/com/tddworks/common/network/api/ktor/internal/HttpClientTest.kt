package com.tddworks.common.network.api.ktor.internal

import io.ktor.client.engine.okhttp.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class HttpClientTest {

    @Test
    fun `should return OkHttp engine`() {
        val engineFactory = httpClientEngine()
        assertTrue(engineFactory is OkHttp)
    }

}