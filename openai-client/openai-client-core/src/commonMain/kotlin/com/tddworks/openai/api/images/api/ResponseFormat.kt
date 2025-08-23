package com.tddworks.openai.api.images.api

import kotlin.jvm.JvmInline
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class ResponseFormat(val value: String) {
    companion object {
        val base64 = ResponseFormat("b64_json")
        val url = ResponseFormat("url")
    }
}
