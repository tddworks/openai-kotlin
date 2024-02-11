package com.tddworks.openai.api.common

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse<T>(
    val created: Long,
    val data: List<T>,
)