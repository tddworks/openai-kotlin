package com.tddworks.common.network.api.ktor

import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.common.network.api.ktor.internal.exception.PermissionException
import com.tddworks.common.network.api.mockHttpClient
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DefaultHttpRequesterTest {
    private lateinit var httpClient: HttpClient

    @Test
    fun `should throw PermissionException when get 403`(): Unit = runBlocking {
        val mockResponse = """
        {
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }
        """.trimIndent()


        httpClient = mockHttpClient(
            mockResponse = mockResponse,
            mockHttpStatusCode = HttpStatusCode.Forbidden
        )

        val requester = DefaultHttpRequester(httpClient)

        assertThrows<PermissionException> {
            requester.performRequest<String> {
                url(path = "/v1/chat/completions")
            }
        }
    }

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