package com.tddworks.ollama.api.chat

import com.tddworks.ollama.api.json.JsonLenient
import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class OllamaChatRequestTest {

    @Test
    fun `should convert json to object`() {
        // given
        val json =
            """
            {
                "model": "llama3",
                "messages": [{
                   "role": "user",
                   "content": "Why is the sky blue?"
                }],
                "format": "json",
                "keep_alive": "5m",
                "stream": false,
                "options": {
                    "num_predict": 100,
                    "temperature": 0.8,
                    "stop": ["\n", "user:"]
                }
            }
            """
                .trimIndent()

        // when
        val request = JsonLenient.decodeFromString(OllamaChatRequest.serializer(), json)

        // then
        assertEquals("llama3", request.model)
        assertEquals(1, request.messages.size)
        assertEquals("user", request.messages[0].role)
        assertEquals("Why is the sky blue?", request.messages[0].content)
        assertEquals("json", request.format)
        assertEquals("5m", request.keepAlive)

        assertEquals(100, request.options?.get("num_predict"))
        assertEquals(0.8, request.options?.get("temperature"))
        assertEquals(listOf("\n", "user:"), request.options?.get("stop"))
    }

    @Test
    fun `should return dummy request`() {
        // given
        val request = OllamaChatRequest.dummy()

        // then
        assertEquals("llama2", request.model)
        assertEquals(1, request.messages.size)
        assertEquals("user", request.messages[0].role)
        assertEquals("Hello!", request.messages[0].content)
    }
}
