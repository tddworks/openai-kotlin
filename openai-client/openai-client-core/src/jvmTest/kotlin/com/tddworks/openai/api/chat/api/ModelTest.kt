package com.tddworks.openai.api.chat.api


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun `should return model from value`() {
        assertEquals(OpenAIModel.GPT_3_5_TURBO, OpenAIModel("gpt-3.5-turbo"))
        assertEquals(OpenAIModel.GPT_3_5_TURBO_0125, OpenAIModel("gpt-3.5-turbo-0125"))
        assertEquals(OpenAIModel.GPT_4_TURBO, OpenAIModel("gpt-4-turbo"))
        assertEquals(OpenAIModel.GPT4_VISION_PREVIEW, OpenAIModel("gpt-4-vision-preview"))
        assertEquals(OpenAIModel.GPT_4O, OpenAIModel("gpt-4o"))
        assertEquals(OpenAIModel.DALL_E_2, OpenAIModel("dall-e-2"))
        assertEquals(OpenAIModel.DALL_E_3, OpenAIModel("dall-e-3"))
    }

    @Test
    fun `should return correct model values`() {
        assertEquals("gpt-3.5-turbo", OpenAIModel.GPT_3_5_TURBO.value)
        assertEquals("gpt-3.5-turbo-0125", OpenAIModel.GPT_3_5_TURBO_0125.value)
        assertEquals("gpt-4-turbo", OpenAIModel.GPT_4_TURBO.value)
        assertEquals("gpt-4-vision-preview", OpenAIModel.GPT4_VISION_PREVIEW.value)
        assertEquals("gpt-4o", OpenAIModel.GPT_4O.value)
        assertEquals("dall-e-2", OpenAIModel.DALL_E_2.value)
        assertEquals("dall-e-3", OpenAIModel.DALL_E_3.value)
    }
}