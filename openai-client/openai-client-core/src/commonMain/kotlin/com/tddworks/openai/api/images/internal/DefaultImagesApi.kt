package com.tddworks.openai.api.images.internal

import com.tddworks.common.network.api.ktor.api.HttpRequester
import com.tddworks.common.network.api.ktor.api.ListResponse
import com.tddworks.common.network.api.ktor.api.performRequest
import com.tddworks.openai.api.images.api.Image
import com.tddworks.openai.api.images.api.ImageCreate
import com.tddworks.openai.api.images.api.Images
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi

internal class DefaultImagesApi(private val requester: HttpRequester) : Images {
    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun generate(request: ImageCreate): ListResponse<Image> {
        return requester.performRequest<ListResponse<Image>> {
            method = HttpMethod.Post
            url(path = Images.IMAGES_GENERATIONS_PATH)
            setBody(request)
            contentType(ContentType.Application.Json)
        }
    }
}

fun Images.Companion.default(requester: HttpRequester): Images =
    DefaultImagesApi(requester)