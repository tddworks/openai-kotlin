package com.tddworks.common.network.api.ktor.api

import kotlinx.serialization.Serializable

@Serializable
data class ListResponse<T>(
    val created: Long,
    val data: List<T>,
)