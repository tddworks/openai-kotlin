package com.tddworks.openai.api.legacy.completions.api.internal

import com.tddworks.common.network.api.ktor.internal.DefaultHttpRequester
import com.tddworks.openai.api.common.mockHttpClient
import com.tddworks.openai.api.legacy.completions.api.CompletionRequest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DefaultCompletionsTest {

    @Test
    fun `should return completion`() = runBlocking {
        val request =
            CompletionRequest(
                prompt = "Once upon a time",
                suffix = "The end",
                maxTokens = 10,
                temperature = 0.5,
            )

        val completions =
            DefaultCompletionsApi(
                requester =
                    DefaultHttpRequester(
                        httpClient =
                            mockHttpClient(
                                """
                                {
                                  "id": "cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7",
                                  "object": "text_completion",
                                  "created": 1589478378,
                                  "model": "gpt-3.5-turbo-instruct",
                                  "system_fingerprint": "fp_44709d6fcb",
                                  "choices": [
                                    {
                                      "text": "\n\nThis is indeed a test",
                                      "index": 0,
                                      "logprobs": null,
                                      "finish_reason": "length"
                                    }
                                  ],
                                  "usage": {
                                    "prompt_tokens": 5,
                                    "completion_tokens": 7,
                                    "total_tokens": 12
                                  }
                                }
                                """
                                    .trimIndent()
                            )
                    )
            )

        val r = completions.completions(request)

        with(r) {
            assertEquals("cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7", id)
            assertEquals(1589478378, created)
            assertEquals("gpt-3.5-turbo-instruct", model)
            assertEquals(1, choices.size)
            assertEquals("\n\nThis is indeed a test", choices[0].text)
            assertEquals(0, choices[0].index)
            assertEquals("length", choices[0].finishReason)
            assertEquals(5, usage?.promptTokens)
            assertEquals(7, usage?.completionTokens)
            assertEquals(12, usage?.totalTokens)
        }
    }
}
