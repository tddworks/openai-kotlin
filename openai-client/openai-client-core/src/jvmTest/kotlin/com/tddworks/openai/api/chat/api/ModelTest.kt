package com.tddworks.openai.api.chat.api


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ModelTest {

    @Test
    fun `should return model from value`() {
        assertEquals(Model.GPT_3_5_TURBO, Model("gpt-3.5-turbo"))
        assertEquals(Model.GPT_3_5_TURBO_0125, Model("gpt-3.5-turbo-0125"))
    }

    @Test
    fun `should return correct model values`() {
        assertEquals("gpt-3.5-turbo", Model.GPT_3_5_TURBO.value)
        assertEquals("gpt-3.5-turbo-0125", Model.GPT_3_5_TURBO_0125.value)
        assertEquals("gpt-4-turbo", Model.GPT_4_TURBO.value)
        assertEquals("gpt-4-vision-preview", Model.GPT4_VISION_PREVIEW.value)
        assertEquals("dall-e-2", Model.DALL_E_2.value)
        assertEquals("dall-e-3", Model.DALL_E_3.value)
    }
}