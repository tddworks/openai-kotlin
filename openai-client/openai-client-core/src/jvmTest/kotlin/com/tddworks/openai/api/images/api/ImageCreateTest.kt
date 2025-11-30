package com.tddworks.openai.api.images.api

import com.tddworks.openai.api.chat.api.OpenAIModel
import com.tddworks.openai.api.common.prettyJson
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ImageCreateTest {

    @Test
    fun `should create image with custom model setting`() {
        // Given
        val json =
            """
            {
              "prompt": "some prompt",
              "model": "dall-e-3"
            }
            """
                .trimIndent()

        val createImage =
            ImageCreate.create(
                prompt = "some prompt",
                size = null,
                style = null,
                quality = null,
                model = OpenAIModel.DALL_E_3,
            )

        // When
        val result = prettyJson.encodeToString(createImage)

        // Then
        assertEquals(json, result)
    }

    @Test
    fun `should create image with all custom settings`() {
        // Given
        val json =
            """
            {
              "prompt": "some prompt",
              "model": "dall-e-3",
              "response_format": "url",
              "size": "1024x1024",
              "style": "vivid",
              "quality": "hd"
            }
            """
                .trimIndent()

        val createImage =
            ImageCreate.create(
                prompt = "some prompt",
                model = OpenAIModel.DALL_E_3,
                size = Size.size1024x1024,
                style = Style.vivid,
                quality = Quality.hd,
                format = ResponseFormat.url,
            )

        // When
        val result = prettyJson.encodeToString(createImage)

        // Then
        assertEquals(json, result)
    }

    @Test
    fun `should create image with default settings`() {
        // Given
        val json =
            """
            {
              "prompt": "A cute baby sea otter",
              "model": "dall-e-3"
            }
            """
                .trimIndent()

        val createImage = ImageCreate.create(prompt = "A cute baby sea otter")

        // When
        val result = prettyJson.encodeToString(createImage)

        // Then
        assertEquals(json, result)
    }
}
