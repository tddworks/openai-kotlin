package com.tddworks.common.network.api.ktor

import app.cash.turbine.test
import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.common.network.api.ktor.api.streamRequest
import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.common.network.api.ktor.internal.default
import com.tddworks.common.network.api.ktor.internal.exception.*
import com.tddworks.common.network.api.mockHttpClient
import com.tddworks.di.initKoin
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.junit5.AutoCloseKoinTest

class DefaultHttpRequesterTest : AutoCloseKoinTest() {
    private lateinit var httpClient: HttpClient

    @JvmField
    @RegisterExtension
    // This extension is used to set the main dispatcher to a test dispatcher
    // launch coroutine eagerly
    // same scheduling behavior as would have in a real app/production
    val testKoinCoroutineExtension = TestKoinCoroutineExtension(StandardTestDispatcher())

    @BeforeEach
    fun setUp() {
        initKoin()
    }

    @Test
    fun `should throw RateLimitException when status code is 401`(): Unit = runBlocking {
        val mockResponse =
            """
        {
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }
        """
                .trimIndent()

        httpClient =
            mockHttpClient(
                mockResponse = mockResponse,
                mockHttpStatusCode = HttpStatusCode.Unauthorized,
            )

        val requester = HttpRequester.default(httpClient)

        assertThrows<AuthenticationException> {
            requester.performRequest<String> { url(path = "/v1/chat/completions") }
        }
    }

    @Test
    fun `should throw RateLimitException when status code is 40x`(): Unit = runBlocking {
        val mockResponse =
            """
        {
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }
        """
                .trimIndent()

        httpClient =
            mockHttpClient(
                mockResponse = mockResponse,
                mockHttpStatusCode = HttpStatusCode.BadRequest,
            )

        val requester = HttpRequester.default(httpClient)

        assertThrows<InvalidRequestException> {
            requester.performRequest<String> { url(path = "/v1/chat/completions") }
        }
    }

    @Test
    fun `should throw RateLimitException when status code is unknown`(): Unit = runBlocking {
        val mockResponse =
            """
        {
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }
        """
                .trimIndent()

        httpClient =
            mockHttpClient(
                mockResponse = mockResponse,
                mockHttpStatusCode = HttpStatusCode.InternalServerError,
            )

        val requester = HttpRequester.default(httpClient)

        assertThrows<UnknownAPIException> {
            requester.performRequest<String> { url(path = "/v1/chat/completions") }
        }
    }

    @Test
    fun `should throw RateLimitException when status code is 429`(): Unit = runBlocking {
        val mockResponse =
            """
        {
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }
        """
                .trimIndent()

        httpClient =
            mockHttpClient(
                mockResponse = mockResponse,
                mockHttpStatusCode = HttpStatusCode.TooManyRequests,
            )

        val requester = HttpRequester.default(httpClient)

        assertThrows<RateLimitException> {
            requester.performRequest<String> { url(path = "/v1/chat/completions") }
        }
    }

    @Test
    fun `should throw PermissionException when get 403 in stream`(): Unit = runBlocking {
        httpClient =
            mockHttpClient(
                """{
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }""",
                mockHttpStatusCode = HttpStatusCode.Forbidden,
            )

        val requester = HttpRequester.default(httpClient)

        requester
            .streamRequest<StreamResponse> { url(path = "/v1/chat/completions") }
            .test(timeout = 10.seconds) { assertTrue(awaitError() is PermissionException) }
    }

    @Test
    fun `should throw PermissionException when get 403`(): Unit = runBlocking {
        val mockResponse =
            """
        {
            "error": {
                "code": null,
                "type": "server_error",
                "param": null,
                "message": "You are accessing the API from an unsupported country, region, or territory."
            }
        }
        """
                .trimIndent()

        httpClient =
            mockHttpClient(
                mockResponse = mockResponse,
                mockHttpStatusCode = HttpStatusCode.Forbidden,
            )

        val requester = HttpRequester.default(httpClient)

        assertThrows<PermissionException> {
            requester.performRequest<String> { url(path = "/v1/chat/completions") }
        }
    }

    @Test
    fun `should not return chat stream completion response when it's empty response`() =
        runBlocking {
            httpClient = mockHttpClient("")

            val requester = DefaultHttpRequester(httpClient)

            requester
                .streamRequest<StreamResponse> { url(path = "/v1/chat/completions") }
                .test {
                    expectNoEvents()
                    cancel()
                }
        }

    @Test
    fun `should return chat stream completion response`() = runTest {
        val mockResponse = StreamResponse("some-content")

        httpClient = mockHttpClient("""data: {"content": "some-content"}""")

        val requester = DefaultHttpRequester(httpClient)

        requester
            .streamRequest<StreamResponse> { url(path = "/v1/chat/completions") }
            .test {
                assertEquals(mockResponse, awaitItem())
                awaitComplete()
            }
    }

    @Test
    fun `should return chat completion response`() = runBlocking {
        val mockResponse =
            """
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
        """
                .trimIndent()

        httpClient = mockHttpClient(mockResponse)

        val requester = DefaultHttpRequester(httpClient)

        val result = requester.performRequest<String> { url(path = "/v1/chat/completions") }

        assertEquals(mockResponse, result)
    }
}
