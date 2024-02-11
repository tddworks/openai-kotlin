package com.tddworks.openai.api.images.api

import com.tddworks.openai.api.common.ListResponse

/**
 * Given a prompt and/or an input image, the model will generate a new image.
 * @see [Images API](https://platform.openai.com/docs/api-reference/images)
 */
interface Images {

    /**
     * Creates an image given a prompt.
     * Get images as URLs or base64-encoded JSON.
     * @param request image creation request.
     * @return list of images.
     */
    suspend fun generate(request: ImageCreate): ListResponse<Image>

    companion object {
        const val IMAGES_GENERATIONS_PATH = "/v1/images/generations"
    }
}