package com.tddworks.openai.api.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Enum representing the available GPT models.
 */
@Serializable
enum class Model(val modelName: String) {
    @SerialName("gpt-3.5-turbo")
    GPT_3_5_TURBO("gpt-3.5-turbo"),

    @SerialName("gpt-3.5-turbo-0125")
    GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125"),

    @SerialName("gpt-4")
    GPT_4("gpt-4"),

    @SerialName("gpt-4-0125-preview")
    GPT_4_TURBO_PREVIEW("gpt-4-0125-preview"),

    @SerialName("gpt-4-turbo-preview")
    GPT_4_TURBO("gpt-4-turbo-preview");

    companion object {
        private val modelMap = values().associateBy(Model::modelName)

        fun fromString(model: String): Model =
            modelMap[model] ?: throw IllegalArgumentException("Unknown model: $model")
    }
}