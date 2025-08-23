package com.tddworks.openai.api.images.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

/**
 * The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024 for dall-e-2.
 * Must be one of 1024x1024, 1792x1024, or 1024x1792 for dall-e-3 models.
 */
@JvmInline
@Serializable
value class Size(val value: String) {

    companion object {
        val size256x256: Size = Size("256x256")
        val size512x512: Size = Size("512x512")
        val size1024x1024: Size = Size("1024x1024")
        val size1792x1024: Size = Size("1792x1024")
        val size1024x1792: Size = Size("1024x1792")
    }
}
