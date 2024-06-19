package com.tddworks.openai.api.legacy.completions.api

import com.tddworks.openai.api.common.prettyJson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CompletionTest {

    @Test
    fun `should return dummy completion`() {
        val completion = Completion.dummy()

        with(completion) {
            assertEquals("id", id)
            assertEquals(0, created)
            assertEquals("model", model)
            assertEquals("systemFingerprint", systemFingerprint)
            assertEquals("object", `object`)
            assertNotNull(usage)
        }
    }

    @Test
    fun `should return completion with required fields`() {
        val completion = Completion(
            id = "cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7",
            choices = listOf(
                CompletionChoice(
                    text = "\n\nThis is indeed a test",
                    index = 0
                )
            ),
            created = 1589478378,
            model = "gpt-3.5-turbo-instruct"
        )

        with(completion) {
            assertEquals("cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7", id)
            assertEquals(1589478378, created)
            assertEquals("gpt-3.5-turbo-instruct", model)
            assertEquals(1, choices.size)
            assertEquals("\n\nThis is indeed a test", choices[0].text)
            assertEquals(0, choices[0].index)
            assertEquals("", choices[0].finishReason)
            assertNull(choices[0].logprobs)
            assertNull(usage)
        }
    }

    @Test
    fun `should return completion`() {
        val completionJson = """
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
        """.trimIndent()

        val completion = prettyJson.decodeFromString<Completion>(completionJson)


        with(completion) {
            assertEquals("cmpl-uqkvlQyYK7bGYrRHQ0eXlWi7", id)
            assertEquals(1589478378, created)
            assertEquals("gpt-3.5-turbo-instruct", model)
            assertEquals(1, choices.size)
            assertEquals("\n\nThis is indeed a test", choices[0].text)
            assertEquals(0, choices[0].index)
            assertEquals("length", choices[0].finishReason)
            assertNull(choices[0].logprobs)
            assertEquals(5, usage?.promptTokens)
            assertEquals(7, usage?.completionTokens)
            assertEquals(12, usage?.totalTokens)
        }
    }
}