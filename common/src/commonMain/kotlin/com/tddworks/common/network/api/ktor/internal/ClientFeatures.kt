package com.tddworks.common.network.api.ktor.internal

import kotlinx.serialization.json.Json

data class ClientFeatures(
    val json: Json = Json,
    val queryParams: () -> Map<String, String> = { emptyMap() },
    val expectSuccess: Boolean = true,
)
