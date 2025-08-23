package com.tddworks.common.network.api.ktor.internal

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class HostPortConnectionConfigTest {

    @Test
    fun `should return default protocol and port`() {
        val config = HostPortConnectionConfig { "example.com" }

        assertNull(config.protocol(), "Protocol should default to null")

        assertNull(config.port(), "Port should default to null")
    }

    @Test
    fun `should able to specified protocol and port`() {
        val protocol: () -> String? = { "http" }
        val port: () -> Int? = { 8080 }
        val config =
            HostPortConnectionConfig(protocol = protocol, port = port, host = { "example.com" })

        assertEquals("http", config.protocol(), "Protocol should be 'http'")
        assertEquals(8080, config.port(), "Port should be 8080")
    }
}
