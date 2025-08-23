package com.tddworks.openai.api.images.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

/**
 * The quality of the image that will be generated. hd creates images with finer details and greater
 * consistency across the image. This param is only supported for dall-e-3. Defaults to standard
 */
@Serializable
@JvmInline
value class Quality(val value: String) {
    companion object {
        val hd = Quality("hd")
        val standard = Quality("standard")
    }
}
