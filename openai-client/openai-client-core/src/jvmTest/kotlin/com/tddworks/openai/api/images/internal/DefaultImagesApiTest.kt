package com.tddworks.openai.api.images.internal

import com.tddworks.openai.api.chat.api.Model
import com.tddworks.openai.api.common.mockHttpClient
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.internal.network.ktor.DefaultHttpRequester
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DefaultImagesApiTest {

    @Test
    fun `should return generated images`() = runBlocking {
        val request = ImageCreate(
            prompt = "Hello! How can I assist you today?",
            model = Model.DALL_E_3
        )

        val chat = DefaultImagesApi(
            requester = DefaultHttpRequester(
                httpClient = mockHttpClient(
                    """
                    {
                      "created": 1589478378,
                      "data": [
                        {
                          "url": "https://..."
                        },
                        {
                          "url": "https://..."
                        }
                      ]
                    }
                """.trimIndent()
                )
            )
        )


        val r = chat.generate(request)

        with(r) {
            assertEquals(1589478378, created)
            assertEquals(2, data.size)
            assertEquals("https://...", data[0].url)
            assertEquals("https://...", data[1].url)
        }
    }
}