package com.tddworks.openai.api.images

import com.tddworks.openai.api.common.ListResponse

/**
 * Given a prompt and/or an input image, the model will generate a new image.
 * @see [Images API](https://platform.openai.com/docs/api-reference/images)
 */
interface Images {

    suspend fun generates(request: ImageCreate): ListResponse<Image>

    companion object {
        const val IMAGES_GENERATIONS_PATH = "/v1/images/generations"
    }
}