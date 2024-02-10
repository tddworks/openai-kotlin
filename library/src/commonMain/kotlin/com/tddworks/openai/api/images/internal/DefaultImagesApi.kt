package com.tddworks.openai.api.images.internal

import com.tddworks.openai.api.common.ListResponse
import com.tddworks.openai.api.images.Image
import com.tddworks.openai.api.images.ImageCreate
import com.tddworks.openai.api.images.Images
import com.tddworks.openai.api.internal.network.ktor.HttpRequester
import com.tddworks.openai.api.internal.network.ktor.performRequest
import io.ktor.client.request.*
import io.ktor.http.*

class DefaultImagesApi(private val requester: HttpRequester) : Images {
    override suspend fun generates(request: ImageCreate): ListResponse<Image> {
        return requester.performRequest<ListResponse<Image>> {
            method = HttpMethod.Post
            url(path = Images.IMAGES_GENERATIONS_PATH)
            setBody(request)
            contentType(ContentType.Application.Json)
        }
    }
}