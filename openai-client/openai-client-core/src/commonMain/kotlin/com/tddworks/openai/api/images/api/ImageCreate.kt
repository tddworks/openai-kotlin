package com.tddworks.openai.api.images.api

import com.tddworks.openai.api.chat.api.Model
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Creates an image given a prompt.
 * @see [ImageCreate](https://platform.openai.com/docs/api-reference/images/create)
 */
@Serializable
@ExperimentalSerializationApi
data class ImageCreate(
    /**
     * A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2 and 4000 characters for dall-e-3.
     */
    @SerialName("prompt")
    val prompt: String,

    /**
     * The model to use for image generation.
     * Defaults to dall-e-2
     */
    @SerialName("model")
    val model: Model = Model.DALL_E_2,

    /**
     * The number of images to generate. Must be between 1 and 10. For dall-e-3, only n=1 is supported.
     * Defaults to 1
     */
    @SerialName("n")
    val n: Int? = null,


    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     * Defaults to url
     */
    @SerialName("response_format")
    val responseFormat: ResponseFormat? = null,

    /**
     * The size of the generated images.
     * Defaults to 1024x1024
     */
    @SerialName("size")
    val size: Size? = null,

    /**
     * The style of the generated images.
     * Must be one of vivid or natural.
     * Vivid causes the model to lean towards generating hyper-real and dramatic images.
     * Natural causes the model to produce more natural, less hyper-real looking images.
     * This param is only supported for dall-e-3.
     */
    @SerialName("style")
    val style: Style? = null,

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    @SerialName("user")
    val user: String? = null,

    /**
     * The quality of the image that will be generated. `Quality.HD` creates images with finer details and greater
     * consistency across the image. This param is only supported for `dall-e-3`.
     */
    @SerialName("quality")
    val quality: Quality? = null,
) {
    companion object {
        /**
         * Create an instance of [ImageCreate].
         * @param prompt A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2 and 4000 characters for dall-e-3.
         * @param model The model to use for image generation. Defaults to dall-e-2
         */
        fun create(
            prompt: String,
            model: Model = Model.DALL_E_3,
        ): ImageCreate = ImageCreate(
            prompt = prompt,
            model = model
        )

        /**
         * Create an instance of [ImageCreate].
         * @param prompt A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2 and 4000 characters for dall-e-3.
         * @param model The model to use for image generation. Defaults to dall-e-2
         * @param size The size of the generated images. Defaults to 1024x1024
         * @param style The style of the generated images. Must be one of vivid or natural. Vivid causes the model to lean towards generating hyper-real and dramatic images. Natural causes the model to produce more natural, less hyper-real looking images. This param is only supported for dall-e-3.
         * @param quality The quality of the image that will be generated. `Quality.HD` creates images with finer details and greater consistency across the image. This param is only supported for `dall-e-3`.
         * @param format The format in which the generated images are returned. Must be one of url or b64_json.
         */
        fun create(
            prompt: String,
            model: Model,
            size: Size? = null,
            style: Style? = null,
            quality: Quality? = null,
            format: ResponseFormat? = null,
        ): ImageCreate = ImageCreate(
            prompt = prompt,
            model = model,
            size = size,
            style = style,
            quality = quality,
            responseFormat = format
        )
    }
}