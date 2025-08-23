package com.tddworks.openai.api.images.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
/**
 * The style of the generated images. Must be one of vivid or natural. Vivid causes the model to
 * lean towards generating hyper-real and dramatic images. Natural causes the model to produce more
 * natural, less hyper-real looking images. This param is only supported for dall-e-3.
 */
value class Style(val value: String) {
    companion object {
        val vivid = Style("vivid")
        val natural = Style("natural")
    }
}
