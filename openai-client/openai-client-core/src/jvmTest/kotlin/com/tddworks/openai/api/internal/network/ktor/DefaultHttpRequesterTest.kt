package com.tddworks.openai.api.internal.network.ktor

import com.tddworks.openai.api.common.mockHttpClient
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DefaultHttpRequesterTest {
    private lateinit var httpClient: HttpClient

    @Test
    fun `should return chat completion response`() = runBlocking {
        val mockResponse = """
            {
              "id": "chatcmpl-123",
              "object": "chat.completion",
              "created": 1677652288,
              "model": "gpt-3.5-turbo-0613",
              "system_fingerprint": "fp_44709d6fcb",
              "choices": [{
                "index": 0,
                "message": {
                  "role": "assistant",
                  "content": "\n\nHello there, how may I assist you today?",
                },
                "logprobs": null,
                "finish_reason": "stop"
              }],
              "usage": {
                "prompt_tokens": 9,
                "completion_tokens": 12,
                "total_tokens": 21
              }
            }
        """.trimIndent()

        httpClient = mockHttpClient(mockResponse)

        val requester = DefaultHttpRequester(httpClient)

        val result = requester.performRequest<String> {
            url(path = "/v1/chat/completions")
        }

        assertEquals(mockResponse, result)
    }
}