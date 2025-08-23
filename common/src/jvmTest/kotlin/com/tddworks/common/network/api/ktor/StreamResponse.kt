package com.tddworks.common.network.api.ktor

import kotlinx.serialization.Serializable

@Serializable data class StreamResponse(val content: String)
