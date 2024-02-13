package com.tddworks.openai.api.images.api

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class ResponseFormat(val value: String) {
    companion object {
        val base64 = ResponseFormat("b64_json")
        val url = ResponseFormat("url")
    }
}