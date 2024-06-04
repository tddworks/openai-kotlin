package com.tddworks.common.network.api.ktor.internal.exception

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class OpenAIErrorTest {

    @Test
    fun `should convert OpenAIError to OpenAIErrorDetails`(){
        val openAIError = OpenAIError(OpenAIErrorDetails("code", "message", "param", "type"))
        val openAIErrorDetails = openAIError.detail
        assertEquals("code", openAIErrorDetails?.code)
        assertEquals("message", openAIErrorDetails?.message)
        assertEquals("param", openAIErrorDetails?.param)
        assertEquals("type", openAIErrorDetails?.type)
    }

}