package com.tddworks.openai.api.legacy.completions.api

import com.tddworks.openai.api.common.prettyJson
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CompletionRequestTest {
    @Test
    fun `should return to correct stream json`() {
        val chatCompletionRequest =
            CompletionRequest.asStream(prompt = "some-prompt", suffix = "some-suffix")

        val result = prettyJson.encodeToString(chatCompletionRequest)

        assertEquals(
            """
            {
              "model": "gpt-3.5-turbo-instruct",
              "prompt": "some-prompt",
              "stream": true,
              "suffix": "some-suffix"
            }
            """
                .trimIndent(),
            result,
        )
    }
}
